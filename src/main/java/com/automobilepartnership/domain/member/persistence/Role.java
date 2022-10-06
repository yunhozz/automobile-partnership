package com.automobilepartnership.domain.member.persistence;

import lombok.Getter;

@Getter
public enum Role {

    /**
     * USER : email 로 검증 완료한 회원
     * GUEST : 회원가입 또는 소셜 로그인만 완료한 회원
     */

    ADMIN("ROLE_ADMIN", "운영자"),
    USER("ROLE_USER", "일반 사용자"),
    GUEST("ROLE_GUEST", "게스트");

    private final String key;
    private final String value;

    Role(String key, String value) {
        this.key = key;
        this.value = value;
    }
}