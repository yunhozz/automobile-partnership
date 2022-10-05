package com.automobilepartnership.api.dto;

import lombok.Getter;

@Getter
public class Success<T> implements Result {

    private final T data;

    public Success(T data) {
        this.data = data;
    }
}