package com.xxx.xcx01.support.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.xxx.xcx01.support.util.JWTUtil;
import com.xxx.xcx01.support.util.PayloadDTO;
import com.xxx.xcx01.support.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        XCXAdminAuthenticationToken adminAuthenticationToken = (XCXAdminAuthenticationToken)authentication;


        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setUserId(adminAuthenticationToken.getId());
        payloadDTO.setUsername(adminAuthenticationToken.getUsername());
        payloadDTO.setSub("xcx");
        payloadDTO.setIat(new Date().getTime());

        String token = null;
        try {
            token = JWTUtil.generateToken(payloadDTO);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("msg", "success");
        resMap.put("code", 0);
        resMap.put("token", token);

        String resJson = jsonMapper.writeValueAsString(resMap);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(resJson);
        response.flushBuffer();
    }

}