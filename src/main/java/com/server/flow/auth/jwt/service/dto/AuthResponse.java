package com.server.flow.auth.jwt.service.dto;

import com.server.flow.employee.entity.enums.Role;

public record AuthResponse(
	Long employeeId,
	String employeeNumber,
	Role role,
	String accessToken,
	String refreshToken
) {
	public static AuthResponse of(
		Long employeeId,
		String employeeNumber,
		Role role,
		String accessToken,
		String refreshToken
	) {
		return new AuthResponse(
			employeeId,
			employeeNumber,
			role,
			accessToken,
			refreshToken
		);
	}
}
