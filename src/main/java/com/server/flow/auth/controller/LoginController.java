package com.server.flow.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.auth.cookie.CookieProcessor;
import com.server.flow.auth.jwt.service.dto.AuthResponse;
import com.server.flow.auth.service.LoginService;
import com.server.flow.auth.service.dto.LoginRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;
	private final CookieProcessor cookieProcessor;

	@PostMapping("/api/login")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> login(
		@Valid @RequestBody LoginRequest request,
		HttpServletResponse response
	) {
		AuthResponse auth = loginService.login(request);
		cookieProcessor.addCookies(response, auth.accessToken(), auth.refreshToken());
		return ResponseEntity.ok("로그인이 완료되었습니다.");
	}
}
