package com.server.flow.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;
import com.server.flow.employee.service.dto.response.EmployeeDetailResponse;
import com.server.flow.employee.service.dto.response.EmployeeOverviewResponse;
import com.server.flow.employee.service.dto.response.EmployeeOverviewsResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeSearchService {
	private final EmployeeRepository employeeRepository;

	public EmployeeDetailResponse searchEmployee(String name) {
		Employee foundEmployee = employeeRepository.findByName(name)
			.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.NOT_EXISTED_EMPLOYEE_MESSAGE));

		return EmployeeDetailResponse.from(foundEmployee);
	}

	public EmployeeOverviewsResponse searchEmployees(Pageable pageable) {
		Page<Employee> page = employeeRepository.findAll(pageable);

		Page<EmployeeOverviewResponse> employeeOverviews = page.map(
			employee -> EmployeeOverviewResponse.of(
				employee.getId(),
				employee.getEmployeeNumber(),
				employee.getName(),
				employee.getDepartment().getDepartmentName(),
				employee.getPosition().getPositionName(),
				employee.getJoinDate(),
				employee.getRoleTypes()
			));

		return EmployeeOverviewsResponse.from(
			employeeOverviews.getContent(),
			page.getNumber(),
			page.getTotalElements(),
			getRemainingDataCount(page),
			page.hasContent(),
			page.hasPrevious(),
			page.hasNext()
		);
	}

	private long getRemainingDataCount(Page<Employee> page) {
		return page.getTotalElements() - ((long)page.getNumber() * page.getSize() + page.getContent().size());
	}
}
