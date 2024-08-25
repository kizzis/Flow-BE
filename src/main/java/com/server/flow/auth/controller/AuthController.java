package com.server.flow.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.auth.service.AuthService;
import com.server.flow.auth.service.TokenService;
import com.server.flow.auth.service.dto.LoginRequest;
import com.server.flow.employee.entity.Employee;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final TokenService tokenService;

	@PostMapping("/api/login")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> login(
		@Valid @RequestBody LoginRequest request,
		HttpServletResponse response
	) {
		Employee employee = authService.login(request);
		//AuthResponse authResponse = tokenService.generateToken(employee);
		return ResponseEntity.ok("로그인이 완료되었습니다.");
	}
}
