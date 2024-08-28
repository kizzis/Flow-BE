package com.server.flow.auth.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.flow.auth.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
