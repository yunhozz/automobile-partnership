package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.api.dto.member.MemberRequestDto;
import com.automobilepartnership.domain.member.service.exception.EmailDuplicateException;
import com.automobilepartnership.domain.member.service.exception.PasswordMismatchException;
import com.automobilepartnership.domain.member.dto.MemberResponseDto;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
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
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    Long userId;

    @BeforeEach
    void beforeEach() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("test@gmail.com", "123", "tester", 12, "seoul", null);
        userId = memberService.join(memberRequestDto);
    }

    @Test
    void join() throws Exception {
        //then
        assertThat(userId).isNotNull();
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void joinFail() throws Exception {
        //given
        MemberRequestDto memberRequestDto1 = new MemberRequestDto("test@gmail.com", "123", "tester1", 12, "seoul", null);
        MemberRequestDto memberRequestDto2 = new MemberRequestDto("test@gmail.com", "456", "tester2", 34, "seoul", null);

        //when
        try {
            memberService.join(memberRequestDto1);
            memberService.join(memberRequestDto2); // 이메일 중복
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(EmailDuplicateException.class);
        }
    }

    @Test
    void updatePassword() throws Exception {
        //when
        memberService.updatePassword(userId, "123", "123123");
        Member result = memberRepository.findById(1L).get();

        //then
        assertThat(result.getPassword()).isEqualTo("123123");
    }

    @Test
    void updatePasswordFail() throws Exception {
        //when
        try {
            memberService.updatePassword(userId, "wrong pw", "123123"); // 기존 비밀번호와 불일치
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(PasswordMismatchException.class);
        }
    }

    @Test
    void withdraw() throws Exception {
        //when
        memberService.withdraw(userId, "123");
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void withdrawFail() throws Exception {
        //when
        try {
            memberService.withdraw(userId, "wrong pw"); // 기존 비밀번호와 불일치
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(PasswordMismatchException.class);
        }
    }

    @Test
    void findMemberDto() throws Exception {
        //when
        MemberResponseDto result = memberService.findMemberDto(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result).extracting("email", "name").containsExactly("test@gmail.com", "tester");
    }

    @Test
    void findMemberDtoList() throws Exception {
        //given
        MemberRequestDto memberRequestDto1 = new MemberRequestDto("test1@gmail.com", "123", "tester1", 12, "seoul", null);
        MemberRequestDto memberRequestDto2 = new MemberRequestDto("test2@gmail.com", "123", "tester2", 12, "seoul", null);
        MemberRequestDto memberRequestDto3 = new MemberRequestDto("test3@gmail.com", "123", "tester3", 12, "seoul", null);
        memberService.join(memberRequestDto1);
        memberService.join(memberRequestDto2);
        memberService.join(memberRequestDto3);

        //when
        List<MemberResponseDto> result = memberService.findMemberDtoList();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).extracting("name").containsExactly("tester", "tester1", "tester2", "tester3");
    }
}