package com.server.flow.attendance.service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CheckInRequest(
	@NotNull(message = "출근 날짜 정보가 필요합니다.")
	LocalDate attendanceDate
) {
}
