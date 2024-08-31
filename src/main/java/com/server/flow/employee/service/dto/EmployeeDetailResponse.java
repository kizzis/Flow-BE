package com.server.flow.employee.service.dto;

import com.server.flow.employee.entity.Employee;

public record EmployeeDetailResponse(Employees employees) {
	public static EmployeeDetailResponse from(Employee employee) {
		Employees employeesDto = Employees.of(
			employee.getId(),
			employee.getEmployeeNumber(),
			employee.getName(),
			employee.getDepartment().getDepartmentName(),
			employee.getPosition().getPositionName(),
			employee.getJoinDate(),
			employee.getRole().toString()
		);

		return new EmployeeDetailResponse(employeesDto);
	}
}
