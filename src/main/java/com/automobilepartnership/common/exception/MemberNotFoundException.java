package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}