package com.server.flow.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.flow.position.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
