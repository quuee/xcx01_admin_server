package com.xxx.xcx01.support.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class XCXAdminAuthenticationToken extends AbstractAuthenticationToken {

    private Long id;

    private String username;

    private String password;

    private String avatarUrl;

    /**
     * 生成未认证的AuthenticationToken
     * @param username
     * @param password
     */
    public XCXAdminAuthenticationToken(String username,String password) {
        super(null);
        this.username=username;
        this.password=password;
        super.setAuthenticated(false);
    }

    /**
     * 生成已认证的AuthenticationToken
     * @param id
     * @param username
     * @param password
     * @param avatarUrl
     * @param authorities
     */
    public XCXAdminAuthenticationToken(Long id, String username, String password, String avatarUrl, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id=id;
        this.username=username;
        this.password=password;
        this.avatarUrl=avatarUrl;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
