package com.server.flow.attendance.entity.enums;

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
}
