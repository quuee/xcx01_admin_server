package com.xxx.xcx01.web.entity.admin;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

@TableName("t_admin")
public class AdminEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
