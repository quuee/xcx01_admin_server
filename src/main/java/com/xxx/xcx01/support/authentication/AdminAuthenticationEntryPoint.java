package com.xxx.xcx01.support.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 未认证处理
 */
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("msg", "unAuthentication");
        resMap.put("code", 401);

        String res = jsonMapper.writeValueAsString(resMap);
        response.getWriter().write(res);
        response.flushBuffer();
    }

}
