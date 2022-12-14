package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.api.dto.member.MemberRequestDto;
import com.automobilepartnership.common.BaseInfo;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.member.service.exception.EmailDuplicateException;
import com.automobilepartnership.domain.member.service.exception.MemberNotFoundException;
import com.automobilepartnership.domain.member.service.exception.PasswordMismatchException;
import com.automobilepartnership.domain.member.dto.MemberResponseDto;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
import com.automobilepartnership.domain.member.persistence.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(MemberRequestDto memberRequestDto) {
        if (memberRepository.findAll().stream().anyMatch(member -> member.getEmail().equals(memberRequestDto.getEmail()))) {
            throw new EmailDuplicateException(ErrorCode.EMAIL_DUPLICATED);
        }
        Member member = createMember(memberRequestDto);
        return memberRepository.save(member).getId();
    }

    @Transactional
    public void updatePassword(Long userId, String oldPw, String newPw) {
        Member member = findMember(userId);

        if (!encoder.matches(oldPw, member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISMATCH);
        }
        member.updatePassword(newPw);
    }

    @Transactional
    public void withdraw(Long userId, String password) {
        Member member = findMember(userId);

        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISMATCH);
        }
        memberRepository.delete(member); // TODO : soft delete?
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberDto(Long userId) {
        return new MemberResponseDto(findMember(userId));
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMemberDtoList() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    private Member createMember(MemberRequestDto memberRequestDto) {
        BaseInfo baseInfo = new BaseInfo(memberRequestDto.getName(), memberRequestDto.getAge(), memberRequestDto.getResidence());

        return Member.builder()
                .email(memberRequestDto.getEmail())
                .password(encoder.encode(memberRequestDto.getPassword()))
                .baseInfo(baseInfo)
                .imageUrl(memberRequestDto.getImageUrl())
                .provider(null)
                .role(Role.GUEST)
                .build();
    }

    private Member findMember(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}