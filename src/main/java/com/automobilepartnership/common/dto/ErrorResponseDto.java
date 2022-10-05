package com.automobilepartnership.common.dto;

import com.automobilepartnership.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private Integer code;
    private String message;
    private List<NotValidResponseDto> fieldErrors;

    public ErrorResponseDto(ErrorCode errorCode) {
        code = errorCode.getCode();
        message = errorCode.getMessage();
    }

    public ErrorResponseDto(ErrorCode errorCode, List<NotValidResponseDto> fieldErrors) {
        code = errorCode.getCode();
        message = errorCode.getMessage();
        this.fieldErrors = fieldErrors;
    }
}