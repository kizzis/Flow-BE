package com.server.flow.employee.service.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EmployeeOverviewResponse(
	Long employeeId,
	String employeeNumber,
	String name,
	String department,
	String position,
	LocalDate joinDate,
	List<String> role
) {
	public static EmployeeOverviewResponse of(
		Long employeeId,
		String employeeNumber,
		String name,
		String department,
		String position,
		LocalDate joinDate,
		String role
	) {
		return new EmployeeOverviewResponse(
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
