package com.zs.qunfx.constant;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/12 11:06
 * @Description:
 */
public enum HTTPState {
    PARAM_ERROR(400, "param error"),
    SYSTEM_ERROR(500, "system error"),
    OK(200, "success"),
    BUSINESS_ERROR(10001, "business error");

    private int code;
    private String msg;

    HTTPState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
