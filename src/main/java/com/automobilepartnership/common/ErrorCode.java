package com.automobilepartnership.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // global
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    NOT_VALID(400, "입력값이 올바르지 않습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}