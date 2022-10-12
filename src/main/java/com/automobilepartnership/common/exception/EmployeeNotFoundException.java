package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmployeeNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}