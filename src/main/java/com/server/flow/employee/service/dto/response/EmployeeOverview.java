package com.server.flow.employee.service.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EmployeeOverview(
	Long employeeId,
	String employeeNumber,
	String name,
	String department,
	String position,
	LocalDate joinDate,
	List<String> role
) {
	public static EmployeeOverview of(
		Long employeeId,
		String employeeNumber,
		String name,
		String department,
		String position,
		LocalDate joinDate,
		String role
	) {
		return new EmployeeOverview(
			employeeId,
			employeeNumber,
			name,
			department,
			position,
			joinDate,
			List.of(role)
		);
	}
}
