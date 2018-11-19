package com.pain.flame.service;

import com.pain.flame.common.ConfigProvider;
import com.pain.flame.exception.BaseException;

/**
 * Created by Administrator on 2018/9/20.
 */
public interface BaseService {
    void refreshAccessToken();
    void setConfigProvider(ConfigProvider configProvider);

    String sendMessage(String message) throws BaseException;
}
