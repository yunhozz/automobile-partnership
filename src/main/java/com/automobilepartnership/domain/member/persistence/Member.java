package com.automobilepartnership.domain.member.persistence;

import com.automobilepartnership.common.BaseInfo;
import com.automobilepartnership.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    private BaseInfo baseInfo;

    private String imageUrl;

    @Column(length = 10)
    private String provider; // oauth2 제공자 (google, kakao, naver)

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, USER, GUEST

    @Builder
    private Member(String email, String password, BaseInfo baseInfo, String imageUrl, String provider, Role role) {
        this.email = email;
        this.password = password;
        this.baseInfo = baseInfo;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.role = role;
    }

    public Member updateNameAndImage(String name, String imageUrl) {
        baseInfo.updateName(name);
        this.imageUrl = imageUrl;

        return this;
    }

    public void updateInfo(String name, int age, String residence) {
        baseInfo.updateInfo(name, age, residence);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    // 이메일 인증 완료시
    public void promote() {
        if (role == Role.GUEST) {
            role = Role.USER;
        } else {
            throw new IllegalStateException("이미 인증 완료한 회원입니다.");
        }
    }
}