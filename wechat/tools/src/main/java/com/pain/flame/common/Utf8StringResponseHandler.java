package com.pain.flame.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2018/9/26.
 */
public class Utf8StringResponseHandler extends BasicResponseHandler {

    public static final Utf8StringResponseHandler INSTANCE = new Utf8StringResponseHandler();

    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }

        if (entity != null) {
            return EntityUtils.toString(entity, Charset.forName("UTF-8"));
        }

        return null;
    }
}
