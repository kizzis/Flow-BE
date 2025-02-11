package com.server.flow.employee.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.department.entity.Department;
import com.server.flow.department.service.DepartmentAddService;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.entity.EmployeeRole;
import com.server.flow.employee.entity.Role;
import com.server.flow.employee.entity.enums.RoleType;
import com.server.flow.employee.repository.EmployeeRepository;
import com.server.flow.employee.repository.EmployeeRoleRepository;
import com.server.flow.employee.repository.RoleRepository;
import com.server.flow.employee.service.dto.request.AddEmployeeRequest;
import com.server.flow.position.entity.Position;
import com.server.flow.position.service.PositionAddService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAddService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeRoleRepository employeeRoleRepository;
	private final RoleRepository roleRepository;
	private final DepartmentAddService departmentAddService;
	private final PositionAddService positionAddService;
	private final PasswordEncoder passwordEncoder;

	public void addEmployee(AddEmployeeRequest request) {
		validateEmployeeNumber(request.employeeNumber());

		String encodedPassword = passwordEncoder.encode(formatJointDateToString(request.joinDate()));

		Department department = departmentAddService.getAndCreateDepartment(request.department());

		Position position = positionAddService.getAndCreatePosition(request.position());

		Employee employee = request.toEmployee();
		employee.addEmployeeInfo(encodedPassword, department, position);
		employeeRepository.save(employee);
		
		Role role = findOrCreateEmployeeRole();
		EmployeeRole employeeRole = EmployeeRole.of(employee, role);
		employeeRoleRepository.save(employeeRole);
	}

	private void validateEmployeeNumber(String employeeNumber) {
		if (employeeRepository.existsByEmployeeNumber(employeeNumber)) {
			throw new IllegalArgumentException(ExceptionConstants.ALREADY_EXISTED_EMPLOYEE_MESSAGE);
		}
	}

	private String formatJointDateToString(LocalDate joinDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return joinDate.format(formatter);
	}

	private Role findOrCreateEmployeeRole() {
		return roleRepository.findByRoleType(RoleType.EMPLOYEE)
			.orElseGet(() -> roleRepository.save(Role.createEmployeeRole()));
	}
}
