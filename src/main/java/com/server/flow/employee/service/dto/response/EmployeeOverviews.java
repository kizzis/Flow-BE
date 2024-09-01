package com.server.flow.employee.service.dto.response;

import java.util.List;

public record EmployeeOverviews(
	List<EmployeeOverview> employeeOverviews,
	int currentPageNumber,
	long totalElements,
	long remainingDataCount,
	boolean hasContent,
	boolean hasPrevious,
	boolean hasNext
) {
	public static EmployeeOverviews from(
		List<EmployeeOverview> employeeOverviews,
		int currentPageNumber,
		long totalElements,
		long remainingDataCount,
		boolean hasContent,
		boolean hasPrevious,
		boolean hasNext
	) {
		return new EmployeeOverviews(
			employeeOverviews,
			currentPageNumber,
			totalElements,
			remainingDataCount,
			hasContent,
			hasPrevious,
			hasNext
		);
	}
}
