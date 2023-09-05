package com.xxx.xcx01.support.authentication;

import com.xxx.xcx01.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 认证提供者
 */
@Component
public class XCXAdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        XCXAdminAuthenticationToken authenticationToken = (XCXAdminAuthenticationToken)authentication;

        AdminDetails adminDetails = userService.loadUserByUsername(authenticationToken.getUsername());

//        String encode = passwordEncoder.encode("123456");
//        System.out.println(encode);
        //密码匹配
        boolean matches = passwordEncoder.matches(authenticationToken.getPassword(), adminDetails.getPassword());
        if(!matches){
            throw new BadCredentialsException("username or password error");
        }

        XCXAdminAuthenticationToken adminAuthenticationToken = new XCXAdminAuthenticationToken(
                adminDetails.getId(),
                adminDetails.getUsername(),
                "",
                adminDetails.getAvatarUrl(),
                adminDetails.getAuthorities());

        return adminAuthenticationToken;
    }

    /**
     * 根据token类型判断使用哪个Provider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return XCXAdminAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
