package com.server.flow.employee.service.dto.response;

import java.util.List;

public record EmployeeDetailsResponse(List<EmployeeDetailResponse> employeeDetails) {
	public static EmployeeDetailsResponse of(List<EmployeeDetailResponse> employeeDetails) {
		return new EmployeeDetailsResponse(employeeDetails);
	}
}
