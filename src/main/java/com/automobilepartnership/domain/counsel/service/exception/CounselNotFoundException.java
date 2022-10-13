package com.automobilepartnership.domain.counsel.service.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class CounselNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CounselNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}