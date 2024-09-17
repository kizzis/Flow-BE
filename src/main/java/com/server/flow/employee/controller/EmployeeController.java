package com.server.flow.employee.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.auth.security.EmployeeAuthorizationUtil;
import com.server.flow.auth.security.dto.EmployeeBasicInfo;
import com.server.flow.common.constants.EmployeeConstants;
import com.server.flow.common.response.ApiResponse;
import com.server.flow.employee.service.EmployeeAddService;
import com.server.flow.employee.service.EmployeeSearchService;
import com.server.flow.employee.service.dto.request.AddEmployeeRequest;
import com.server.flow.employee.service.dto.response.EmployeeDetailResponse;
import com.server.flow.employee.service.dto.response.EmployeeOverviewsResponse;

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
	public ApiResponse<EmployeeBasicInfo> getEmployeeBasicInfo() {
		EmployeeBasicInfo employeeBasicInfo = EmployeeAuthorizationUtil.getEmployeeBasicInfo();
		return ApiResponse.success(employeeBasicInfo, EmployeeConstants.EMPLOYEEID_SUCCESS_COMPLETED_MESSAGE);
	}

	@GetMapping("/api/admin/employees/search")
	public ApiResponse<EmployeeDetailResponse> searchEmployee(@RequestParam String name) {
		EmployeeDetailResponse employeeDetailResponse = employeeSearchService.searchEmployee(name);
		return ApiResponse.success(employeeDetailResponse, EmployeeConstants.EMPLOYEE_SEARCH_COMPLETED_MESSAGE);
	}

	@GetMapping("/api/admin/employees")
	public ApiResponse<EmployeeOverviewsResponse> searchPagedEmployees(
		@PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		EmployeeOverviewsResponse employeeOverviewsResponse = employeeSearchService.searchEmployees(pageable);
		return ApiResponse.success(employeeOverviewsResponse, EmployeeConstants.ALL_EMPLOYEES_SEARCH_COMPLETED_MESSAGE);
	}
}
