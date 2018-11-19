package com.pain.flame.common;

/**
 * Created by Administrator on 2018/9/20.
 */
public class AccessToken {
    private String token;
    private int expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public static AccessToken fromJson(String json) {
        return null;
    }
}
