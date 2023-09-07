package com.xxx.xcx01.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.xcx01.support.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    @Autowired
    private AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;

    @Autowired
    private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private XCXAdminAuthenticationProvider xcxAdminAuthenticationProvider;

    @Autowired
    private AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler;

    @Autowired
    private AdminAuthenticationFailureHandler adminAuthenticationFailureHandler;

    @Autowired
    private ObjectMapper jsonMapper;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .requestMatchers("/static/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement((sessionManagement)->{
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        httpSecurity.authorizeHttpRequests((authorizeRequests)->{
            authorizeRequests.anyRequest().authenticated();

        }).exceptionHandling((exceptionHandling)->{
            exceptionHandling.authenticationEntryPoint(adminAuthenticationEntryPoint);
        });

        httpSecurity.addFilterAt(xcxAdminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, XCXAdminAuthenticationFilter.class);
//        httpSecurity.authenticationManager(providerManager());

        return httpSecurity.build();
    }

    @Bean
    public XCXAdminAuthenticationFilter xcxAdminAuthenticationFilter() throws Exception {
        XCXAdminAuthenticationFilter xcxAdminAuthenticationFilter = new XCXAdminAuthenticationFilter();
        xcxAdminAuthenticationFilter.setJsonMapper(jsonMapper);
        xcxAdminAuthenticationFilter.setAuthenticationManager(providerManager());
        xcxAdminAuthenticationFilter.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
        xcxAdminAuthenticationFilter.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
        return xcxAdminAuthenticationFilter;
    }

    @Bean
    protected ProviderManager providerManager() {
        return new ProviderManager(xcxAdminAuthenticationProvider);
    }



}
