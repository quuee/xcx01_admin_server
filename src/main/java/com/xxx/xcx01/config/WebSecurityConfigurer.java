package com.xxx.xcx01.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.xcx01.support.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
// 开启注解设置权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


	@Autowired
	private XCXAdminAuthenticationProvider xcxAdminAuthenticationProvider;

	@Autowired
	private AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler;

	@Autowired
	private AdminAuthenticationFailureHandler adminAuthenticationFailureHandler;

	@Autowired
	private AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;


	@Autowired
	private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	@Autowired
	private ObjectMapper jsonMapper;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(xcxAdminAuthenticationProvider);
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**","/images/**");
//		WebSecurity是Spring Security对外的唯一出口，而HttpSecurity只是内部安全策略的定义方式；
//		WebSecurity对标FilterChainProxy，而HttpSecurity则对标SecurityFilterChain，另外它们的父类都是AbstractConfiguredSecurityBuilder
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests().anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(adminAuthenticationEntryPoint);
		http.addFilterAt(xcxAdminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationTokenFilter,XCXAdminAuthenticationFilter.class);

	}

	@Bean
	public XCXAdminAuthenticationFilter xcxAdminAuthenticationFilter() throws Exception {
		XCXAdminAuthenticationFilter xcxAdminAuthenticationFilter = new XCXAdminAuthenticationFilter();
		xcxAdminAuthenticationFilter.setJsonMapper(jsonMapper);
		xcxAdminAuthenticationFilter.setAuthenticationManager(authenticationManager());
		xcxAdminAuthenticationFilter.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
		xcxAdminAuthenticationFilter.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
		return xcxAdminAuthenticationFilter;
	}


}
