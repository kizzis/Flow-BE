package com.server.flow.auth.jwt.dto;

import com.server.flow.employee.entity.enums.Role;

public record JwtPayloadResponse(
	Long employeeId,
	String employeeNumber,
	Role role
) {
}
