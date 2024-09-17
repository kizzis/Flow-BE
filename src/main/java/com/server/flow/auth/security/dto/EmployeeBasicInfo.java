package com.server.flow.auth.security.dto;

import java.util.List;

import com.server.flow.auth.security.PrincipalDetails;
import com.server.flow.employee.entity.enums.RoleType;

public record EmployeeBasicInfo(
	Long employeeId,
	List<RoleType> roleTypes
) {
	public static EmployeeBasicInfo from(PrincipalDetails principal) {
		return new EmployeeBasicInfo(
			principal.getEmployeeId(),
			principal.getRoleTypes()
		);
	}
}
