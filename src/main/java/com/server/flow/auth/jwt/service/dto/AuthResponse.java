package com.server.flow.auth.jwt.service.dto;

import java.util.List;

import com.server.flow.employee.entity.enums.RoleType;

public record AuthResponse(
	Long employeeId,
	String employeeNumber,
	List<RoleType> roleTypes,
	String accessToken,
	String refreshToken
) {
	public static AuthResponse of(
		Long employeeId,
		String employeeNumber,
		List<RoleType> roleTypes,
		String accessToken,
		String refreshToken
	) {
		return new AuthResponse(
			employeeId,
			employeeNumber,
			roleTypes,
			accessToken,
			refreshToken
		);
	}
}
