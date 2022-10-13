package com.automobilepartnership.domain.counsel.service.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class EmployeeDifferentException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmployeeDifferentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}