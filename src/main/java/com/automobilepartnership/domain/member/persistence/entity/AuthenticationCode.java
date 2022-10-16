package com.automobilepartnership.domain.member.persistence.entity;

import com.automobilepartnership.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticationCode extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(length = 8)
    private String code;

    private LocalDateTime expireDate; // 만료 시간

    private boolean isVerified; // 검증 여부

    public AuthenticationCode(Member member, String code, LocalDateTime expireDate) {
        this.member = member;
        this.code = code;
        this.expireDate = expireDate;
    }

    public void verify() {
        if (!isVerified) {
            isVerified = true;
            member.promote();
        } else {
            throw new IllegalStateException("이미 검증이 완료된 토큰입니다.");
        }
    }
}