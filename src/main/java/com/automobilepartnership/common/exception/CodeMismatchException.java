package com.automobilepartnership.common.exception;

import com.automobilepartnership.common.ErrorCode;
import lombok.Getter;

@Getter
public class CodeMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public CodeMismatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}