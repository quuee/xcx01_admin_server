package com.xxx.xcx01.support.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import com.xxx.xcx01.support.exception.ErrorCode;
import com.xxx.xcx01.support.exception.ServerException;
import com.xxx.xcx01.support.util.JWTUtil;
import com.xxx.xcx01.web.service.AdminService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.text.ParseException;

@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {


    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationTokenFilter.class);

    @Autowired
    private AdminService userService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        logger.info("url:{},token:{}",request.getRequestURI(),token);
        if(!ObjectUtils.isEmpty(token)){
            LinkedTreeMap<String,Object> payload = null;
            try{
                // 全局异常只能拦截到controller层，解决方法：handlerExceptionResolver
                // handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.TOKENEXPIRED));
                payload = JWTUtil.verify(token); // 如果token过期，JWT解析时会抛出异常TokenExpiredException
            }
            catch (ParseException | JOSEException e) {
                handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.INTERNAL_SERVER_ERROR));
                return;

            } catch (ServerException e){
                if(e.getCode() == ErrorCode.TOKENEXPIRED.getCode()){
                    handlerExceptionResolver.resolveException(request,response,null,new ServerException(ErrorCode.TOKENEXPIRED));
                    return;
                }
            }

            if(!ObjectUtils.isEmpty(payload)){
                String adminName = (String) payload.get("adminName");
//                PayloadDTO payloadDTO = jsonMapper.readValue(string, PayloadDTO.class);
                AdminDetails adminDetails = userService.loadUserByUsername(adminName);
                XCXAdminAuthenticationToken adminAuthenticationToken = new XCXAdminAuthenticationToken(
                        adminDetails.getAid(),
                        adminDetails.getUsername(),
                        "",
                        adminDetails.getAvatarUrl(),
                        adminDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(adminAuthenticationToken);
            }

        }

        filterChain.doFilter(request,response);
    }


}
