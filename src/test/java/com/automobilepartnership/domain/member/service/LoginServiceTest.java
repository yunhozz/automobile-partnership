package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.api.dto.member.MemberRequestDto;
import com.automobilepartnership.domain.member.service.exception.PasswordMismatchException;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
import com.automobilepartnership.domain.member.persistence.entity.RefreshToken;
import com.automobilepartnership.domain.member.persistence.repository.RefreshTokenRepository;
import com.automobilepartnership.security.jwt.TokenResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void beforeEach() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("test@gmail.com", "123", "tester", 12, "seoul", null);
        memberService.join(memberRequestDto);
    }

    @Test
    void login() throws Exception {
        //given
        Member member = memberRepository.findById(1L).get();

        //when
        TokenResponseDto result = loginService.login(member.getEmail(), "123");
        List<RefreshToken> tokens = refreshTokenRepository.findAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getGrantType()).isEqualTo("Bearer");
        assertThat(tokens).isNotEmpty();
        assertThat(tokens.size()).isEqualTo(1);
        assertThat(result.getRefreshToken()).isEqualTo(tokens.get(0).getToken());
    }

    @Test
    void loginFail() throws Exception {
        //given
        Member member = memberRepository.findById(1L).get();

        //when
        try {
            loginService.login(member.getEmail(), "wrong pw"); // 비밀번호 불일치
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(PasswordMismatchException.class);
        }
    }
}