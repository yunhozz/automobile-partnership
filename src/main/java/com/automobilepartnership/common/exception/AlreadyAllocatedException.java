package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyAllocatedException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyAllocatedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}