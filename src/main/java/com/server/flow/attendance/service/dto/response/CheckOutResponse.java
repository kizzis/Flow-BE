package com.server.flow.attendance.service.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.server.flow.attendance.entity.Attendance;

public record CheckOutResponse(
	Long attendanceId,
	LocalDate attendanceDate,
	LocalTime checkOutTime,
	String attendanceStatus
) {
	public static CheckOutResponse from(Attendance attendance) {
		return new CheckOutResponse(
			attendance.getId(),
			attendance.getAttendanceDate(),
			attendance.getCheckOutTime(),
			attendance.getStatus().getDescription()
		);
	}
}
