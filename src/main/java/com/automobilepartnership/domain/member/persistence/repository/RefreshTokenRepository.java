package com.automobilepartnership.domain.member.persistence.repository;

import com.automobilepartnership.domain.member.persistence.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}