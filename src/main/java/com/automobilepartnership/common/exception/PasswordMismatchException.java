package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public PasswordMismatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}