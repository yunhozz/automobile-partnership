package com.automobilepartnership.domain.member.persistence;

import com.automobilepartnership.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    private String email;

    private String password;

    @Column(length = 10)
    private String name;

    @Column(columnDefinition = "tinyint")
    private int age;

    private String imageUrl;

    @Column(length = 10)
    private String provider; // oauth2 제공자 (google, kakao, naver)

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, USER, GUEST

    @Builder
    private Member(String email, String password, String name, int age, String imageUrl, String provider, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.role = role;
    }

    public Member update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;

        return this;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    // 이메일 인증 완료시
    public void promote() {
        if (role == Role.GUEST) {
            role = Role.USER;
        }
    }
}