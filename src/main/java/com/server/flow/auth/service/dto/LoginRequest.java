package com.server.flow.auth.service.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
	@NotBlank(message = "사원번호는 필수로 입력해야 합니다.")
	String employeeNumber,
	@NotBlank(message = "비밀번호는 필수로 입력해야 합니다")
	String password
) {
}
