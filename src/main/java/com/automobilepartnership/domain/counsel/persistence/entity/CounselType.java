package com.automobilepartnership.domain.counsel.persistence.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CounselType {

    MAINTENANCE("정비 요청"),
    ETC("기타");

    private final String desc;

    CounselType(String desc) {
        this.desc = desc;
    }

    public static CounselType getCode(String desc) {
        return Arrays.stream(CounselType.values())
                .filter(counselType -> counselType.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(String.format("상담 타입에 %s가 존재하지 않습니다.", desc)));
    }
}