package com.automobilepartnership.domain.member.persistence.repository;

import com.automobilepartnership.domain.member.persistence.entity.AuthenticationCode;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {

    Optional<AuthenticationCode> findByMemberAndExpireDateAfter(Member member, LocalDateTime now);
}