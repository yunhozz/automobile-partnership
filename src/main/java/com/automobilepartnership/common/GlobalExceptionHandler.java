package com.automobilepartnership.common;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.common.dto.ErrorResponseDto;
import com.automobilepartnership.common.dto.NotValidResponseDto;
import com.automobilepartnership.domain.counsel.service.exception.AlreadyAllocatedException;
import com.automobilepartnership.domain.counsel.service.exception.EmployeeDifferentException;
import com.automobilepartnership.domain.member.service.exception.AuthCodeNotFoundException;
import com.automobilepartnership.domain.member.service.exception.CodeMismatchException;
import com.automobilepartnership.domain.counsel.service.exception.CounselNotFoundException;
import com.automobilepartnership.domain.member.service.exception.EmailDuplicateException;
import com.automobilepartnership.domain.member.service.exception.EmailNotFoundException;
import com.automobilepartnership.domain.counsel.service.exception.EmployeeNotFoundException;
import com.automobilepartnership.domain.member.service.exception.MemberNotFoundException;
import com.automobilepartnership.domain.notification.service.exception.NotificationNotFoundException;
import com.automobilepartnership.domain.member.service.exception.PasswordMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(RuntimeException.class)
    public Response handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.INTER_SERVER_ERROR);
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Response handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.ACCESS_DENIED);
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
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
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public Response handleMemberNotFoundException(MemberNotFoundException e) {
        log.error("handleMemberNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public Response handleEmailDuplicateException(EmailDuplicateException e) {
        log.error("handleEmailDuplicateException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public Response handleEmailNotFoundException(EmailNotFoundException e) {
        log.error("handleEmailNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public Response handlePasswordMismatchException(PasswordMismatchException e) {
        log.error("handlePasswordMismatchException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(AuthCodeNotFoundException.class)
    public Response handleAuthCodeNotFoundException(AuthCodeNotFoundException e) {
        log.error("handleAuthCodeNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(CodeMismatchException.class)
    public Response handleCodeMismatchException(CodeMismatchException e) {
        log.error("handleCodeMismatchException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public Response handleNotificationNotFoundException(NotificationNotFoundException e) {
        log.error("handleNotificationNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(CounselNotFoundException.class)
    public Response handleCounselNotFoundException(CounselNotFoundException e) {
        log.error("handleCounselNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(AlreadyAllocatedException.class)
    public Response handleAlreadyAllocatedException(AlreadyAllocatedException e) {
        log.error("handleAlreadyAllocatedException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public Response handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        log.error("handleEmployeeNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }

    @ExceptionHandler(EmployeeDifferentException.class)
    public Response handleEmployeeDifferentException(EmployeeDifferentException e) {
        log.error("handleEmployeeDifferentException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());
        return Response.failure(HttpStatus.valueOf(error.getCode()), -1000, error);
    }
}