package com.automobilepartnership.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // global
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    NOT_VALID(400, "입력값이 올바르지 않습니다."),

    // member
    MEMBER_NOT_FOUND(400, "회원을 찾을 수 없습니다."),
    EMAIL_DUPLICATED(400, "중복되는 이메일이 존재합니다."),
    EMAIL_NOT_FOUND(400, "이메일을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(400, "기존 비밀번호와 일치하지 않습니다."),
    CODE_MISMATCH(400, "전송한 코드와 일치하지 않습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}