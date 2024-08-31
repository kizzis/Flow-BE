package com.server.flow.employee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;
import com.server.flow.employee.service.dto.EmployeeDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeSearchService {
	private final EmployeeRepository employeeRepository;

	public EmployeeDetailResponse searchEmployee(Long employeeId) {
		Employee foundEmployee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.NOT_EXISTED_EMPLOYEE_MESSAGE));

		return EmployeeDetailResponse.from(foundEmployee); // todo
	}
}
