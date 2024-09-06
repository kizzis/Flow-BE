package com.server.flow.attendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.flow.attendance.service.CheckInOutService;
import com.server.flow.attendance.service.dto.request.CheckInRequest;
import com.server.flow.attendance.service.dto.response.CheckInResponse;
import com.server.flow.auth.security.EmployeeAuthorizationUtil;
import com.server.flow.common.constants.AttendanceConstants;
import com.server.flow.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class CheckInOutController {
	private final CheckInOutService checkInOutService;

	@PostMapping("/checkin")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest request) {
		Long employeeId = EmployeeAuthorizationUtil.getLoginEmployeeId();
		CheckInResponse checkInResponse = checkInOutService.checkIn(employeeId, request);
		return ApiResponse.success(checkInResponse, AttendanceConstants.CHECK_IN_COMPLETED_MESSAGE);
	}

	// @PostMapping("/checkout")
	// @ResponseStatus(HttpStatus.CREATED)
	// public ApiResponse<CheckOutResponse> checkOut(@Valid @RequestBody CheckOutRequest request) {
	// 	Long employeeId = EmployeeAuthorizationUtil.getLoginEmployeeId();
	// 	CheckOutResponse checkOutResponse = checkInOutService.checkOut(employeeId, request);
	// 	return ApiResponse.success(checkOutResponse, AttendanceConstants.CHECK_OUT_COMPLETED_MESSAGE);
	// }
}
