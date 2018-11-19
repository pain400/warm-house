package com.pain.flame.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/20.
 */
public class ResponseCode {
    private int code;
    private String msg;

    private static final Map<Integer, String> responseMap = new HashMap<Integer, String>();

    static {
        responseMap.put(-1, "系统繁忙");
    }

    public static ResponseCode fromJson(String json) {
        return null;
    }

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

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
