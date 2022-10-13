package com.automobilepartnership.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // global
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    ACCESS_DENIED(401, "권한이 없습니다."),
    NOT_VALID(400, "입력값이 올바르지 않습니다."),

    // member
    MEMBER_NOT_FOUND(400, "회원을 찾을 수 없습니다."),
    EMAIL_DUPLICATED(400, "중복되는 이메일이 존재합니다."),
    EMAIL_NOT_FOUND(400, "이메일을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(400, "기존 비밀번호와 일치하지 않습니다."),
    AUTH_CODE_NOT_FOUND(400, "요청 시간이 만료되었거나 인증 코드가 존재하지 않습니다."),
    CODE_MISMATCH(400, "전송한 코드와 일치하지 않습니다."),

    // notification
    NOTIFICATION_NOT_FOUND(400, "알림을 찾을 수 없습니다."),

    // counsel
    COUNSEL_NOT_FOUND(400, "상담 내역을 찾을 수 없습니다."),
    ALREADY_ALLOCATED(400, "상담사가 이미 배정되어 있습니다."),

    // employee
    EMPLOYEE_NOT_FOUND(400, "직원을 찾을 수 없습니다."),
    EMPLOYEE_DIFFERENT(400, "배정된 직원과 다릅니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}