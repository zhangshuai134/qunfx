package com.zs.qunfx.dto;


import com.zs.qunfx.constant.HTTPState;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/12 11:03
 * @Description: controller 返回
 */
public class BaseResponseDTO<T> {
    /**
     * 返回结果码
     */
    private int code;
    /**
     * 返回结果描述
     */
    private String msg;
    /**
     * 返回结果值
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponseDTO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponseDTO buildSuccess(T data) {
        return  new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), data);
    }


    public static BaseResponseDTO buildSuccess() {
        return  new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), null);
    }

    public static BaseResponseDTO buildError(int code, String msg) {
        return  new BaseResponseDTO(code, msg, null);
    }

    public static BaseResponseDTO buildEexception() {
        return  new BaseResponseDTO(HTTPState.SYSTEM_ERROR.getCode(), HTTPState.SYSTEM_ERROR.getMsg(), null);
    }
}
