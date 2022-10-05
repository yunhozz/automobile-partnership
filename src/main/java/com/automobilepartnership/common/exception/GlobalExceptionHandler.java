package com.automobilepartnership.common.exception;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.common.dto.ErrorResponseDto;
import com.automobilepartnership.common.dto.NotValidResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("handleException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.NOT_FOUND);
        return Response.failure(HttpStatus.NOT_FOUND, -1000, error);
    }

    @ExceptionHandler(RuntimeException.class)
    public Response handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.INTER_SERVER_ERROR);
        return Response.failure(HttpStatus.INTERNAL_SERVER_ERROR, -1000, error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<NotValidResponseDto> notValidResponseDtoList = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            NotValidResponseDto notValid = NotValidResponseDto.builder()
                    .field(fieldError.getField())
                    .code(fieldError.getCode())
                    .rejectValue(fieldError.getRejectedValue())
                    .message(fieldError.getDefaultMessage())
                    .build();
            notValidResponseDtoList.add(notValid);
        }
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.NOT_VALID, notValidResponseDtoList);
        return Response.failure(HttpStatus.BAD_REQUEST, -1000, error);
    }
}