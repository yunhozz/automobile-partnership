package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmailDuplicateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}