package com.pain.flame.service;

import com.pain.flame.common.AccessToken;
import com.pain.flame.common.ConfigProvider;
import com.pain.flame.common.ResponseCode;
import com.pain.flame.common.Utf8StringResponseHandler;
import com.pain.flame.exception.BaseException;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2018/9/20.
 */
public class BaseServiceImpl implements BaseService {

    private static final AtomicBoolean GLOBAL_ACCESS_TOKEN_REFRESH_FLAG = new AtomicBoolean(false);
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private ConfigProvider configProvider;

    /**
     * 保证线程安全，多线程同时刷新，只刷新一次
     */
    public void refreshAccessToken() {
        if (!GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.compareAndSet(false, true)) {
            String url = configProvider.getRefershTokenUrl();

            HttpGet httpGet = new HttpGet(url);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                String content = new BasicResponseHandler().handleResponse(response);

                // TODO 内容错误处理
                AccessToken accessToken = AccessToken.fromJson(content);
                configProvider.updateAccessToken(accessToken.getToken(), accessToken.getExpireTime());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.set(false);
            }

        } else {
            // 等待刷新完毕
            while (GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }
    }

    public void setConfigProvider(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public String sendMessage(String message) throws BaseException {
        return post("", message);
    }

    private String post(String url, String message) throws BaseException {
        return execute("POST", url, message);
    }

    private String get(String url, String message) throws BaseException {
        return execute("GET", url, message);
    }

    private String execute(String method, String url, String message) throws BaseException {
        if (StringUtils.isBlank(configProvider.getAccessToken())) {
            refreshAccessToken();
        }

        String accessToken = configProvider.getAccessToken();
        String urlWithAccessToken = getUrlWithAccessToken(url, accessToken);

        try {
            String content = null;
            if ("POST".equals(method)) {
                HttpPost httpPost = new HttpPost(urlWithAccessToken);

                if (message != null) {
                    StringEntity stringEntity = new StringEntity(message, UTF8);
                    httpPost.setEntity(stringEntity);
                }

                CloseableHttpResponse response = httpClient.execute(httpPost);
                content = Utf8StringResponseHandler.INSTANCE.handleResponse(response);
            } else if ("GET".equals(method)) {

                if (message != null) {
                    urlWithAccessToken = urlWithAccessToken + "&message=" + message;
                }
                HttpGet httpGet = new HttpGet(urlWithAccessToken);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                response.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
                content = Utf8StringResponseHandler.INSTANCE.handleResponse(response);
            }

            ResponseCode responseCode = ResponseCode.fromJson(content);

            if (responseCode.getCode() != 0) {
                throw new BaseException(responseCode);
            }

            return content;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUrlWithAccessToken(String url, String accessToken) {
        if (url.indexOf('?') == -1) {
            return url + "?access_token=" + accessToken;
        } else {
            return url + "&access_token=" + accessToken;
        }
    }
}
