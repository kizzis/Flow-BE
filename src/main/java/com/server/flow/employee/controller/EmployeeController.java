package com.server.flow.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.common.constants.EmployeeConstants;
import com.server.flow.common.response.ApiResponse;
import com.server.flow.employee.service.EmployeeAddService;
import com.server.flow.employee.service.EmployeeSearchService;
import com.server.flow.employee.service.dto.AddEmployeeRequest;
import com.server.flow.employee.service.dto.EmployeeDetailResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeAddService employeeAddService;
	private final EmployeeSearchService employeeSearchService;

	@PostMapping("/api/admin/employees")
	public ApiResponse<Void> addEmployee(@Valid @RequestBody AddEmployeeRequest request) {
		employeeAddService.addEmployee(request);
		return ApiResponse.successWithNoContent(EmployeeConstants.EMPLOYEE_ADDITIONAL_COMPLETE_MESSAGE);
	}

	@GetMapping("/api/admin/employees/{employeeId}")
	public ApiResponse<EmployeeDetailResponse> searchEmployee(@PathVariable Long employeeId) {
		EmployeeDetailResponse employeeDetailResponse = employeeSearchService.searchEmployee(employeeId);
		return ApiResponse.success(employeeDetailResponse, EmployeeConstants.EMPLOYEE_SEARCH_COMPLETED_MESSAGE);
	}
}
