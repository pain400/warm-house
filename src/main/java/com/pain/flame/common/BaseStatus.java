package com.pain.flame.common;

/**
 * Created by Administrator on 2018/10/14.
 */
public enum BaseStatus {
    UNKNOWN_ERROR(-1, "unknown error"),
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "bad request"),
    NOT_FOUND(404, "not found"),
    INTERNAL_SERVER_ERROR(500, "internal server error"),
    INVALID_PARAM(40001, "invalid parameter"),
    NOT_SUPPORTED_OPERATION(40002, "not supported operation"),
    NOT_LOGIN(50000, "not login");

    private int code;
    private String message;

    public static BaseStatus getByMessage(String message) {
        for (BaseStatus baseStatus : BaseStatus.values()) {
            if (baseStatus.getMessage().equals(message)) {
                return baseStatus;
            }
        }

        return UNKNOWN_ERROR;
    }

    public static BaseStatus getByCode(int code) {
        for (BaseStatus baseStatus : BaseStatus.values()) {
            if (baseStatus.getCode() == code) {
                return baseStatus;
            }
        }

        return UNKNOWN_ERROR;
    }

    BaseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
