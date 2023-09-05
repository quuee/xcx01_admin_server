package com.xxx.xcx01.support.exception;


/**
 * 错误编码
 *
 */

public enum ErrorCode {
    UNAUTHORIZED(401, "还未登录，不能访问"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    TOKENEXPIRED(493, "登录凭证过期，请重新登录"),
    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试");

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
