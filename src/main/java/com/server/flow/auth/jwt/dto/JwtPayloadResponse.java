package com.server.flow.auth.jwt.dto;

import java.util.List;

import com.server.flow.employee.entity.enums.RoleType;

public record JwtPayloadResponse(
	Long employeeId,
	String employeeNumber,
	List<RoleType> roleType
) {
}
