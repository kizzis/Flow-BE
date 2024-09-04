package com.server.flow.position.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.position.entity.Position;
import com.server.flow.position.repository.PositionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PositionAddService {
	private final PositionRepository positionRepository;

	public Position getAndCreatePosition(String positionName) {
		Position position = Position.from(positionName);
		return positionRepository.save(position);
	}
}
