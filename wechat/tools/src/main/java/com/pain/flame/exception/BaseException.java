package com.pain.flame.exception;

import com.pain.flame.common.ResponseCode;

/**
 * Created by Administrator on 2018/9/20.
 */
public class BaseException extends Exception {
    private ResponseCode responseCode;

    public BaseException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

}
