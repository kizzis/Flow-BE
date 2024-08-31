package com.server.flow.employee.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.department.entity.Department;
import com.server.flow.department.service.DepartmentAddService;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;
import com.server.flow.employee.service.dto.AddEmployeeRequest;
import com.server.flow.position.entity.Position;
import com.server.flow.position.service.PositionAddService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAddService {
	private final EmployeeRepository employeeRepository;
	private final DepartmentAddService departmentAddService;
	private final PositionAddService positionAddService;
	private final PasswordEncoder passwordEncoder;

	public void addEmployee(AddEmployeeRequest request) {
		validateEmployeeNumber(request.employeeNumber());

		String encodedPassword = passwordEncoder.encode(request.joinDate().toString());

		Department department = departmentAddService.getAndCreateDepartment(request.department());

		Position position = positionAddService.getAndCreatePosition(request.position());

		Employee employee = request.toEmployee();
		employee.addEmployeeInfo(encodedPassword, department, position);

		employeeRepository.save(employee);
	}

	private void validateEmployeeNumber(String employeeNumber) {
		if (employeeRepository.existsByEmployeeNumber(employeeNumber)) {
			throw new IllegalArgumentException("이미 추가된 사원입니다.");
		}
	}
}
