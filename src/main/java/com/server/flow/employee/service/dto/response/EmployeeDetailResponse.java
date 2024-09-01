package com.server.flow.employee.service.dto.response;

import com.server.flow.employee.entity.Employee;

public record EmployeeDetailResponse(EmployeeOverview employeeOverview) {
	public static EmployeeDetailResponse from(Employee employee) {
		EmployeeOverview employeeOverviewDto = EmployeeOverview.of(
			employee.getId(),
			employee.getEmployeeNumber(),
			employee.getName(),
			employee.getDepartment().getDepartmentName(),
			employee.getPosition().getPositionName(),
			employee.getJoinDate(),
			employee.getRole().toString()
		);

		return new EmployeeDetailResponse(employeeOverviewDto);
	}
}
