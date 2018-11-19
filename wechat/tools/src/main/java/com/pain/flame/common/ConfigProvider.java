package com.pain.flame.common;

/**
 * Created by Administrator on 2018/9/20.
 */
public interface ConfigProvider {
    String getAccessToken();
    String getAppId();
    String getSecrect();
    int getExpireTime();

    String getRefershTokenUrl();
    void updateAccessToken(String accessToken, int expireTime);
}
