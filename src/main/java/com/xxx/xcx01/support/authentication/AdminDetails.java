package com.xxx.xcx01.support.authentication;

import com.xxx.xcx01.web.entity.admin.MenuEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDetails implements UserDetails {

    private Long aid;

    private String adminName;

    private String password;

    private String avatarUrl;

    private List<MenuEntity> menuEntities;

    public AdminDetails() {
    }

    public AdminDetails(Long aid, String adminName, String password, String avatarUrl) {
        this.aid = aid;
        this.adminName = adminName;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public AdminDetails(Long aid, String adminName, String password, String avatarUrl, List<MenuEntity> menuEntities) {
        this.aid = aid;
        this.adminName = adminName;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.menuEntities = menuEntities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (menuEntities == null || menuEntities.isEmpty()) {
            return new HashSet<SimpleGrantedAuthority>(0);
        }
        return menuEntities.stream().map(MenuEntity::getAuthority).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public List<MenuEntity> getMenuEntities() {
        return menuEntities;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.adminName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setMenuEntities(List<MenuEntity> menuEntities) {
        this.menuEntities = menuEntities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
