package com.server.flow.auth.security.dto;

import java.util.List;

import com.server.flow.employee.entity.enums.RoleType;

public record EmployeePrincipalDetails(
	Long employeeId, String username,
	String password,
	List<RoleType> roleTypes
) {
}
