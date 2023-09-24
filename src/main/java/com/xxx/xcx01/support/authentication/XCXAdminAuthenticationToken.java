package com.xxx.xcx01.support.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class XCXAdminAuthenticationToken extends AbstractAuthenticationToken {

    private Long id;

    private String adminName;

    private String password;

    private String avatarUrl;

    /**
     * 生成未认证的AuthenticationToken
     * @param adminName
     * @param password
     */
    public XCXAdminAuthenticationToken(String adminName,String password) {
        super(null);
        this.adminName=adminName;
        this.password=password;
        super.setAuthenticated(false);
    }

    /**
     * 生成已认证的AuthenticationToken
     * @param id
     * @param adminName
     * @param password
     * @param avatarUrl
     * @param authorities
     */
    public XCXAdminAuthenticationToken(Long id, String adminName, String password, String avatarUrl, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id=id;
        this.adminName=adminName;
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
        return this.adminName;
    }

    public Long getId() {
        return id;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
