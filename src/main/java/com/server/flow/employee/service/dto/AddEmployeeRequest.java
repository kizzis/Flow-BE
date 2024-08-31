package com.server.flow.employee.service.dto;

import java.time.LocalDate;

import com.server.flow.employee.entity.Employee;

public record AddEmployeeRequest(
	String employeeNumber,
	String name,
	String department,
	String position,
	LocalDate joinDate
) {

	public Employee toEmployee() {
		return Employee.builder()
			.employeeNumber(employeeNumber)
			.name(name)
			.joinDate(joinDate)
			.build();
	}
}
