package com.server.flow.auth.service;

import org.springframework.stereotype.Service;

import com.server.flow.auth.jwt.service.TokenService;
import com.server.flow.auth.jwt.service.dto.AuthResponse;
import com.server.flow.auth.service.dto.LoginRequest;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final EmployeeRepository employeeRepository;
	private final TokenService tokenService;

	public AuthResponse login(@Valid LoginRequest request) {
		Employee employee = employeeRepository.findByEmployeeNumber(request.employeeNumber())
			.orElseThrow(() -> new IllegalArgumentException("사원으로 등록되어 있지 않습니다."));
		
		if (!request.password().equals(employee.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
		}

		return tokenService.generateToken(employee);
	}
}
