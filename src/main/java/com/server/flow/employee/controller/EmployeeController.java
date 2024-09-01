package com.server.flow.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.auth.security.EmployeeAuthorizationUtil;
import com.server.flow.common.constants.EmployeeConstants;
import com.server.flow.common.response.ApiResponse;
import com.server.flow.employee.service.EmployeeAddService;
import com.server.flow.employee.service.EmployeeSearchService;
import com.server.flow.employee.service.dto.request.AddEmployeeRequest;
import com.server.flow.employee.service.dto.response.EmployeeDetailResponse;
import com.server.flow.employee.service.dto.response.EmployeeIdResponse;
import com.server.flow.employee.service.dto.response.EmployeesResponse;

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

	@GetMapping("/api/employees/me")
	public ApiResponse<EmployeeIdResponse> getEmployeeId() {
		Long employeeId = EmployeeAuthorizationUtil.getLoginEmployeeId();
		return ApiResponse.success(EmployeeIdResponse.from(employeeId),
			EmployeeConstants.EMPLOYEEID_SUCCESS_COMPLETED_MESSAGE);
	}

	@GetMapping("/api/admin/employees/{employeeId}")
	public ApiResponse<EmployeeDetailResponse> searchEmployee(@PathVariable Long employeeId) {
		EmployeeDetailResponse employeeDetailResponse = employeeSearchService.searchEmployee(employeeId);
		return ApiResponse.success(employeeDetailResponse, EmployeeConstants.EMPLOYEE_SEARCH_COMPLETED_MESSAGE);
	}

	// todo. 전체 구성원 조회 API 개발 + offset 기반 pagination
	public ApiResponse<EmployeesResponse> searchAllEmployee() {
		return null;
	}
}
