package com.server.flow.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;
import com.server.flow.employee.service.dto.response.EmployeeDetailResponse;
import com.server.flow.employee.service.dto.response.EmployeeOverview;
import com.server.flow.employee.service.dto.response.EmployeeOverviews;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeSearchService {
	private final EmployeeRepository employeeRepository;

	public EmployeeDetailResponse searchEmployee(Long employeeId) {
		Employee foundEmployee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.NOT_EXISTED_EMPLOYEE_MESSAGE));

		return EmployeeDetailResponse.from(foundEmployee); // todo
	}

	public EmployeeOverviews searchEmployees(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			Sort.by(Sort.Direction.DESC, "createdAt")
		);

		Page<Employee> page = employeeRepository.findAll(pageRequest);

		Page<EmployeeOverview> employeeOverviews = page.map(
			employee -> EmployeeOverview.of(
				employee.getId(),
				employee.getEmployeeNumber(),
				employee.getName(),
				employee.getDepartment().getDepartmentName(),
				employee.getPosition().getPositionName(),
				employee.getJoinDate(),
				employee.getRole().getValue()
			));

		return EmployeeOverviews.from(
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
