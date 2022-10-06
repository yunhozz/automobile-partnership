package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class EmailNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmailNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}