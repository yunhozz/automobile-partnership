package com.automobilepartnership.domain.member.persistence.repository;

import com.automobilepartnership.domain.member.persistence.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}