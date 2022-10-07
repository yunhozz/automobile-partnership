package com.automobilepartnership.domain.member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {

    Optional<AuthenticationCode> findByMemberAndExpireDateAfter(Member member, LocalDateTime now);
}