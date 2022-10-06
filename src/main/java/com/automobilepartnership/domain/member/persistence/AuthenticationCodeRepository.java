package com.automobilepartnership.domain.member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {

    Optional<AuthenticationCode> findByMember(Member member);
}