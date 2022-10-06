package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.common.exception.MemberNotFoundException;
import com.automobilepartnership.common.exception.PasswordMismatchException;
import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import com.automobilepartnership.domain.member.persistence.RefreshToken;
import com.automobilepartnership.domain.member.persistence.RefreshTokenRepository;
import com.automobilepartnership.security.jwt.JwtProvider;
import com.automobilepartnership.security.jwt.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISMATCH);
        }
        TokenResponseDto tokenDto = jwtProvider.createTokenDto(member.getEmail(), member.getRole().getKey());
        RefreshToken refreshToken = new RefreshToken(String.valueOf(member.getId()), tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }
}