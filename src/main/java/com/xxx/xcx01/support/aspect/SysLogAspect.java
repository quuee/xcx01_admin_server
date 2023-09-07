package com.xxx.xcx01.support.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonParseException;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import com.xxx.xcx01.support.util.IPUtil;
import com.xxx.xcx01.support.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.stream.Stream;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private ObjectMapper jsonMapper;

    @Pointcut("@annotation(com.xxx.xcx01.support.aspect.SysLog)")
    public void annotationLogPointCut() {
    }

    /**
     * 整个表达式可以分为五个部分：
     * <p>
     * 1、execution(): 表达式主体。
     * <p>
     * 2、第一个*号：表示返回类型，*号表示所有的类型。
     * <p>
     * 3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
     * <p>
     * 4、第二个*号：表示类名，*号表示所有的类。
     * <p>
     * 5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */

//    @Pointcut("execution(public * com.ccx.jsj.web.controller..*.*(..))")
//    public void packageLogPointCut() {
//
//    }

//    @Pointcut("annotationLogPointCut() || packageLogPointCut()")
//    public void bizPoint(){
//
//    }

//    @Around("bizPoint()")
    @Around("annotationLogPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object proceed;
        try {
            proceed = point.proceed();
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            Stream.of(e.getStackTrace()).forEach(stringBuilder::append);
            logger.error(e.getMessage() + ": {}", stringBuilder);
            throw e;
        }
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time, proceed);

        return proceed;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object proceed) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog syslog = method.getAnnotation(SysLog.class);
        String operate = syslog != null ? syslog.operation() : "";


        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String params = "";
        String returnParam = "";
        try {
            //请求的参数
            Object[] args = joinPoint.getArgs();
            params = jsonMapper.writeValueAsString(args);

            //返回的参数
            returnParam = jsonMapper.writeValueAsString(proceed);
        }catch (JsonProcessingException e){
            logger.error("saveSysLog error:{}",e.getMessage());
        }

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IPUtil.getIp(request);

        String username = "unknown";
        String token = request.getHeader("Authorization");
        if(!ObjectUtils.isEmpty(token)){
            try {
                LinkedTreeMap<String, Object> verify = JWTUtil.verify(token);
                username = (String)verify.get("username");
            }catch (ParseException | JOSEException e){
                logger.error("saveSysLog error:{}",e.getMessage());
            }
        }

        logger.info("用户：{}，操作：{}，请求类名：{}，请求方法：{}，参数：{}，返回：{}，ip：{}，耗时：{}",
                username, operate, className, methodName, params, returnParam, ip, time);
    }
}
