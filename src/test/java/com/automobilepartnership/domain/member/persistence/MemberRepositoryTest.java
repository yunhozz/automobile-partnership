package com.automobilepartnership.domain.member.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findByEmail() throws Exception {
        //given
        Member member = Member.builder()
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        //when
        Optional<Member> result1 = memberRepository.findByEmail("test@gmail.com");
        Optional<Member> result2 = memberRepository.findByEmail("test@naver.com");

        //then
        assertThat(result1).isNotNull();
        assertThat(result2).isNotPresent();
    }
}