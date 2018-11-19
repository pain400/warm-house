package com.pain.flame.common;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/14.
 */
@Controller
public class ErrorHandler extends BasicErrorController {

    private static final String ERROR_PATH = "/error";

    public ErrorHandler(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();

        System.out.println("status: " + status);

        ModelAndView modelAndView = new ModelAndView();
        switch (status) {
            case 403:
                modelAndView.setViewName("403");
                break;
            case 404:
                modelAndView.setViewName("404");
                break;
            case 500:
                modelAndView.setViewName("500");
                break;
            default:
                modelAndView.setViewName("index");
        }

        return modelAndView;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(request, includeStackTrace);

        result.remove("timestamp");
        result.remove("status");
        result.remove("error");
        result.remove("exception");
        result.remove("path");

        String msg = (String) result.get("message");
        BaseStatus baseStatus = BaseStatus.getByMessage(msg);
        result.put("code", baseStatus.getCode());
        result.put("message", baseStatus.getMessage());

        return result;
    }
}
