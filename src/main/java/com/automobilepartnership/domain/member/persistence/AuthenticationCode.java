package com.automobilepartnership.domain.member.persistence;

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

    private boolean isVerified;

    public AuthenticationCode(Member member, String code) {
        this.member = member;
        this.code = code;
    }

    public void verify() {
        if (!isVerified) {
            isVerified = true;
            member.promote();
        }
    }
}