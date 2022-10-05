package com.automobilepartnership.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {

    private boolean isSuccess;
    private HttpStatus status;
    private int code;
    private Result result;

    public static Response success(HttpStatus status) {
        return new Response(true, status, 0, null);
    }

    public static <T> Response success(HttpStatus status, T data) {
        return new Response(true, status, 0, new Success<>(data));
    }

    public static <T> Response failure(HttpStatus status, int code, T data) {
        return new Response(false, status, code, new Failure<>(data));
    }
}