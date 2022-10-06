package com.automobilepartnership.domain.member.dto;

import com.automobilepartnership.domain.member.persistence.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Integer age;
    private String provider;
    private String role;

    public MemberResponseDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        age = member.getAge();
        provider = member.getProvider();
        role = member.getRole().getValue();
    }
}