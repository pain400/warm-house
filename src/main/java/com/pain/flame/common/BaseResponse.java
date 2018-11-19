package com.pain.flame.common;

/**
 * Created by Administrator on 2018/10/14.
 */
public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;

    private BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private BaseResponse(BaseStatus baseStatus) {
        this.code = baseStatus.getCode();
        this.message = baseStatus.getMessage();
    }

    private BaseResponse(BaseStatus baseStatus, T data) {
        this(baseStatus);
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> BaseResponse success(T data) {
        return new BaseResponse<>(BaseStatus.SUCCESS, data);
    }

    public static BaseResponse error() {
        return new BaseResponse(BaseStatus.UNKNOWN_ERROR);
    }

    public static BaseResponse error(BaseStatus errorStatus) {
        return new BaseResponse(errorStatus);
    }

    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, message);
    }
}
