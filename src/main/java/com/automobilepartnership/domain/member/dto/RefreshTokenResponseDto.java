package com.automobilepartnership.domain.member.dto;

import com.automobilepartnership.domain.member.persistence.RefreshToken;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RefreshTokenResponseDto {

    private Long id;
    private String userId;
    private String token;
    private LocalDateTime createdDate;

    public RefreshTokenResponseDto(RefreshToken refreshToken) {
        id = refreshToken.getId();
        userId = refreshToken.getUserId();
        token = refreshToken.getToken();
        createdDate = refreshToken.getCreatedDate();
    }
}