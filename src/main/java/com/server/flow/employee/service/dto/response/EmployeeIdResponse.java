package com.server.flow.employee.service.dto.response;

public record EmployeeIdResponse(Long employeeId) {
	public static EmployeeIdResponse from(Long employeeId) {
		return new EmployeeIdResponse(employeeId);
	}
}
