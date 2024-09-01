package com.server.flow.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.server.flow.auth.jwt.service.TokenService;
import com.server.flow.auth.jwt.service.dto.AuthResponse;
import com.server.flow.auth.service.dto.LoginRequest;
import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final EmployeeRepository employeeRepository;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;

	public AuthResponse login(@Valid LoginRequest request) {
		Employee employee = employeeRepository.findByEmployeeNumber(request.employeeNumber())
			.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.UNREGISTERED_EMPLOYEE_MESSAGE));

		String encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.changePassword(encodedPassword);

		validatePasswordMatch(request.password(), employee);

		return tokenService.generateToken(employee);
	}

	private void validatePasswordMatch(String password, Employee employee) {
		if (!passwordEncoder.matches(password, employee.getPassword())) {
			throw new IllegalArgumentException(ExceptionConstants.MISMATCH_PASSWORD_MESSAGE);
		}
	}
}
