package com.server.flow.employee.service.dto.response;

import java.util.List;

public record EmployeeOverviewsResponse(
	List<EmployeeOverviewResponse> employeeOverviewResponse,
	int currentPageNumber,
	long totalElements,
	long remainingDataCount,
	boolean hasContent,
	boolean hasPrevious,
	boolean hasNext
) {
	public static EmployeeOverviewsResponse from(
		List<EmployeeOverviewResponse> employeeOverviewResponse,
		int currentPageNumber,
		long totalElements,
		long remainingDataCount,
		boolean hasContent,
		boolean hasPrevious,
		boolean hasNext
	) {
		return new EmployeeOverviewsResponse(
			employeeOverviewResponse,
			currentPageNumber,
			totalElements,
			remainingDataCount,
			hasContent,
			hasPrevious,
			hasNext
		);
	}
}
