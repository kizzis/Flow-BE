package com.server.flow.attendance.entity.enums;

import java.time.LocalTime;

import com.server.flow.common.constants.AttendanceConstants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AttendanceStatus {
	ABSENCE("결근"),
	WORK("근무"),
	EARLY_LEAVE("조퇴"),
	LEAVE_WORK("퇴근"),
	TARDINESS("지각"),
	VACATION("휴가");

	private final String description;

	// todo. 휴가, 결근
	public static AttendanceStatus getCheckInStatus(LocalTime checkInTime) {
		// 결근
		if (checkInTime == null) {
			return ABSENCE;
		}

		// 지각
		if (checkInTime.isAfter(AttendanceConstants.GENERAL_CHECK_IN_TIME)) {
			return TARDINESS;
		}

		// 근무
		if (checkInTime.isBefore(AttendanceConstants.GENERAL_CHECK_IN_TIME) ||
			checkInTime.equals(AttendanceConstants.GENERAL_CHECK_IN_TIME)) {
			return WORK;
		}

		throw new IllegalArgumentException(AttendanceConstants.NOT_FOUND_ATTENDANCE_STATUS_MESSAGE);
	}

	public static AttendanceStatus getCheckOutStatus(LocalTime checkOutTime) {
		// 조퇴
		if (checkOutTime.isBefore(AttendanceConstants.GENERAL_CHECK_OUT_TIME)) {
			return EARLY_LEAVE;
		}

		// 퇴근
		if (checkOutTime.isAfter(AttendanceConstants.GENERAL_CHECK_OUT_TIME) ||
			checkOutTime.equals(AttendanceConstants.GENERAL_CHECK_OUT_TIME)) {
			return LEAVE_WORK;
		}

		throw new IllegalArgumentException(AttendanceConstants.NOT_FOUND_ATTENDANCE_STATUS_MESSAGE);
	}
}
