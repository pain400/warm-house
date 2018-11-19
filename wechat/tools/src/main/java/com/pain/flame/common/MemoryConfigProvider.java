package com.pain.flame.common;

/**
 * Created by Administrator on 2018/9/26.
 */
public class MemoryConfigProvider implements ConfigProvider {
    private String appId;
    private String secrect;
    private String accessToken;
    private int expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecrect() {
        return secrect;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public String getRefershTokenUrl() {
        return null;
    }

    public void updateAccessToken(String accessToken, int expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }

    public void updateAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken.getToken();
        this.expireTime = accessToken.getExpireTime();
    }
}
