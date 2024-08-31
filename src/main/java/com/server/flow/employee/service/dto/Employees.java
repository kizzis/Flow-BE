package com.server.flow.employee.service.dto;

import java.time.LocalDate;
import java.util.List;

public record Employees(
	Long employeeId,
	String employeeNumber,
	String name,
	String department,
	String position,
	LocalDate joinDate,
	List<String> role
) {
	public static Employees of(
		Long employeeId,
		String employeeNumber,
		String name,
		String department,
		String position,
		LocalDate joinDate,
		String role
	) {
		return new Employees(
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
