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

    private Long id;

    private String username;

    private String password;

    private String avatarUrl;

    private List<MenuEntity> menuEntities;

    public AdminDetails() {
    }

    public AdminDetails(Long id, String username, String password, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public AdminDetails(Long id, String username, String password, String avatarUrl, List<MenuEntity> menuEntities) {
        this.id = id;
        this.username = username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
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
