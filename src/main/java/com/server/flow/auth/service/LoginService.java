package com.server.flow.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.server.flow.auth.jwt.service.TokenService;
import com.server.flow.auth.jwt.service.dto.AuthResponse;
import com.server.flow.auth.service.dto.LoginRequest;
import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.entity.enums.RoleType;
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

		if (isMismatchPassword(request.password(), employee)) {
			throw new IllegalArgumentException(ExceptionConstants.MISMATCH_PASSWORD_MESSAGE);
		}

		return tokenService.generateToken(employee);
	}

	private boolean isMismatchPassword(String rawPassword, Employee employee) {
		return employee.getEmployeeRoles().stream().noneMatch(role -> role.getRole().getRoleType() != RoleType.EMPLOYEE)
			&& !passwordEncoder.matches(rawPassword, employee.getPassword());
	}
}
