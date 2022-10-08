package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class NotificationNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotificationNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}