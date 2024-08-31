package com.server.flow.employee.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.common.response.ApiResponse;
import com.server.flow.employee.service.EmployeeAddService;
import com.server.flow.employee.service.dto.AddEmployeeRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeAddService employeeAddService;
	//private final EmployeeSearchService employeeSearchService;

	@PostMapping("/api/admin/employees")
	public ApiResponse<Void> addEmployee(@Valid @RequestBody AddEmployeeRequest request) {
		employeeAddService.addEmployee(request);
		return ApiResponse.successWithNoContent("구성원 추가가 완료되었습니다.");
	}

	// @GetMapping("/api/admin/employees/{employeeId}")
	// public ApiResponse<EmployeeDetailResponse> searchEmployee(@PathVariable Long employeeId) {
	// 	EmployeeDetailResponse employeeDetailResponse = employeeSearchService.searchEmployee(employeeId);
	// 	return ApiResponse.success(employeeDetailResponse, "구성원 조회가 완료되었습니다.");
	// }
}
