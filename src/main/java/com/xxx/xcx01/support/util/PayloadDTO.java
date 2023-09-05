package com.xxx.xcx01.support.util;


public class PayloadDTO {

    // 主题
    private String sub;
    //签发时间
    private Long iat;
    //过期时间 时间戳

    private Long userId;

    private String username;


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
