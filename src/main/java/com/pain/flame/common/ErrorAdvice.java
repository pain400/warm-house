package com.pain.flame.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/14.
 */

@ControllerAdvice(basePackages = "com.pain.flame.controller")
public class ErrorAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handleException(Exception e) {
        BaseStatus baseStatus = BaseStatus.getByMessage(e.getMessage());
        return BaseResponse.error(baseStatus);
    }
}
