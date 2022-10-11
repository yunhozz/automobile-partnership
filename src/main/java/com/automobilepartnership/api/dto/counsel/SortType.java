package com.automobilepartnership.api.dto.counsel;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortType {

    ENTIRE("전체"),
    RESOLVED("해결"),
    NOT_RESOLVED("미해결");

    private final String desc;

    SortType(String desc) {
        this.desc = desc;
    }

    public static SortType getCode(String desc) {
        return Arrays.stream(SortType.values())
                .filter(sortType -> sortType.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(String.format("분류 타입에 %s가 존재하지 않습니다.", desc)));
    }
}