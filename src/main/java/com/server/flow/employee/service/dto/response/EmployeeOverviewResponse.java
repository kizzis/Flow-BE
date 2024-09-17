package com.server.flow.employee.service.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.server.flow.employee.entity.enums.RoleType;

public record EmployeeOverviewResponse(
	Long employeeId,
	String employeeNumber,
	String name,
	String department,
	String position,
	LocalDate joinDate,
	List<RoleType> roles
) {
	public static EmployeeOverviewResponse of(
		Long employeeId,
		String employeeNumber,
		String name,
		String department,
		String position,
		LocalDate joinDate,
		List<RoleType> roles
	) {
		return new EmployeeOverviewResponse(
			employeeId,
			employeeNumber,
			name,
			department,
			position,
			joinDate,
			roles
		);
	}
}
