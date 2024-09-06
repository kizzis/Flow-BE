package com.server.flow.attendance.service.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.server.flow.attendance.entity.Attendance;

public record CheckInResponse(
	Long attendanceId,
	LocalDate attendanceDate,
	LocalTime checkInTime,
	String attendanceStatus
) {
	public static CheckInResponse from(Attendance attendance) {
		return new CheckInResponse(
			attendance.getId(),
			attendance.getAttendanceDate(),
			attendance.getCheckInTime(),
			attendance.getStatus().getDescription()
		);
	}
}
