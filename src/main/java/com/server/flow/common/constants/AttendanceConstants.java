package com.server.flow.common.constants;

import java.time.LocalTime;

public class AttendanceConstants {
	public static final String CHECK_IN_COMPLETED_MESSAGE = "출근 체크가 완료되었습니다.";
	public static final String CHECK_OUT_COMPLETED_MESSAGE = "퇴근 체크가 완료되었습니다.";
	public static final String ALREADY_EXISTED_CHECK_IN_MESSAGE = "이미 출근했습니다. 출근 버튼을 다시 누를 수 없습니다.";
	public static final String MISMATCHED_ATTENDANCE_DATE_MESSAGE = "출근 날짜가 일치하지 않습니다.";
	public static final String NOT_FOUND_EMPLOYEE_SPECIFIC_ATTENDANCE_MESSAGE = "사원의 출퇴근 날짜 정보를 찾을 수 없습니다.";
	public static final String MISMATCH_ATTENDANCE_DATE_MESSAGE = "당일 날짜에 대해서만 출퇴근 체크가 가능합니다.";
	public static final LocalTime GENERAL_CHECK_IN_TIME = LocalTime.of(10, 0);
	public static final LocalTime GENERAL_CHECK_OUT_TIME = LocalTime.of(18, 0);
	public static final String NOT_FOUND_ATTENDANCE_STATUS_MESSAGE = "체크인 상태를 확인할 수 없습니다.";
}
