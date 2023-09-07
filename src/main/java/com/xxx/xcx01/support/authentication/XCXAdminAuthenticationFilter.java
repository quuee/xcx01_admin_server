package com.xxx.xcx01.support.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 登录过滤器
 */

public class XCXAdminAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private ObjectMapper jsonMapper;

    private static final String defaultFilterProcessesUrl="/user/login";

    public XCXAdminAuthenticationFilter() {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, HttpRequestMethodNotSupportedException, HttpMediaTypeNotSupportedException, MissingServletRequestParameterException {

        if(!"POST".equalsIgnoreCase(request.getMethod())){
            throw new HttpRequestMethodNotSupportedException(request.getMethod());
        }
        if(!"application/json".equalsIgnoreCase(request.getContentType()) && !"application/json;charset=UTF-8".equalsIgnoreCase(request.getContentType())){
            throw new HttpMediaTypeNotSupportedException(request.getContentType());
        }


        JsonNode params = getParams(request);
        JsonNode username = params.get("username");
        JsonNode password = params.get("password");
        if(ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)){
            throw new MissingServletRequestParameterException("username || password","String");
        }

        XCXAdminAuthenticationToken adminAuthenticationToken = new XCXAdminAuthenticationToken(username.asText(), password.asText());
        return this.getAuthenticationManager().authenticate(adminAuthenticationToken);
    }

    private JsonNode getParams(HttpServletRequest request) throws IOException{
        StringBuilder stringBuffer = new StringBuilder();
        String line = null;
        BufferedReader reader = request.getReader();
        while((line = reader.readLine())!=null){
            stringBuffer.append(line);
        }
        String jsonString = stringBuffer.toString().replaceAll("\\s", "").replaceAll("\n", "");
        JsonNode jsonNode = jsonMapper.readTree(jsonString);
        return jsonNode;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }
}
