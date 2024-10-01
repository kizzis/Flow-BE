package com.server.flow.attendance.service.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.server.flow.attendance.entity.Attendance;

public record CheckOutResponse(
	Long attendanceId,
	LocalDate attendanceDate,
	LocalTime checkOutTime,
	String attendanceStatus,
	String workTime
) {
	public static CheckOutResponse from(Attendance attendance) {
		return new CheckOutResponse(
			attendance.getId(),
			attendance.getAttendanceDate(),
			attendance.getCheckOutTime(),
			attendance.getStatus().getDescription(),
			getWorkTime(attendance.getWorkTime())
		);
	}

	private static String getWorkTime(Long workTime) {
		long hours = workTime / 3600;
		long minutes = (workTime % 3600) / 60;
		long seconds = workTime % 60;
		return hours + "시" + minutes + "분" + seconds + "초";
	}
}
