package com.automobilepartnership.domain.member.dto;

import com.automobilepartnership.domain.member.persistence.AuthenticationCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuthenticationCodeResponseDto {

    private Long id;
    private Long userId;
    private String code;
    private LocalDateTime expireDate;
    private boolean isVerified;
    private LocalDateTime createdDate;

    public AuthenticationCodeResponseDto(AuthenticationCode authenticationCode) {
        id = authenticationCode.getId();
        userId = authenticationCode.getMember().getId();
        code = authenticationCode.getCode();
        expireDate = authenticationCode.getExpireDate();
        isVerified = authenticationCode.isVerified();
        createdDate = authenticationCode.getCreatedDate();
    }
}