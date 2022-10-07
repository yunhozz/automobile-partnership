package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class AuthCodeNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthCodeNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}