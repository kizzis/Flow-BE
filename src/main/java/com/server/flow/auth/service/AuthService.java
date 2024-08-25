package com.server.flow.auth.service;

import org.springframework.stereotype.Service;

import com.server.flow.auth.service.dto.LoginRequest;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final EmployeeRepository employeeRepository;
	//private final PasswordEncryptor passwordEncryptor;

	public Employee login(@Valid LoginRequest request) {
		Employee employee = employeeRepository.findByEmployeeNumber(request.employeeNumber())
			.orElseThrow(() -> new IllegalArgumentException("사원으로 등록되어 있지 않습니다."));

		// todo
		//isMatchPassword(request.password(), employee);

		return employee;
	}

	// private void isMatchPassword(String password, Employee employee) {
	// 	boolean isMisMatchPassword = passwordEncryptor.matchPassword(password, employee.getPassword());
	//
	// 	if (isMisMatchPassword) {
	// 		throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
	// 	}
	// }
}
