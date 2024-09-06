package com.server.flow.attendance.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.attendance.entity.Attendance;
import com.server.flow.attendance.repository.AttendanceRepository;
import com.server.flow.attendance.service.dto.request.CheckInRequest;
import com.server.flow.attendance.service.dto.request.CheckOutRequest;
import com.server.flow.attendance.service.dto.response.CheckInResponse;
import com.server.flow.attendance.service.dto.response.CheckOutResponse;
import com.server.flow.common.constants.AttendanceConstants;
import com.server.flow.common.constants.EmployeeConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CheckInOutService {
	private final EmployeeRepository employeeRepository;
	private final AttendanceRepository attendanceRepository;

	public CheckInResponse checkIn(Long employeeId, CheckInRequest request) {
		Employee foundEmployee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new IllegalArgumentException(EmployeeConstants.NOT_FOUND_EMPLOYEE_MESSAGE));

		validateCheckInAttendanceExists(employeeId, request.attendanceDate());

		Attendance attendance = Attendance.of(foundEmployee, request.attendanceDate(), request.checkInTime());
		attendanceRepository.save(attendance);

		return CheckInResponse.from(attendance);
	}

	public CheckOutResponse checkOut(Long employeeId, CheckOutRequest request) {
		Attendance foundAttendance = attendanceRepository.findAttendance(request.attendanceId(), employeeId)
			.orElseThrow(
				() -> new IllegalArgumentException(AttendanceConstants.NOT_FOUND_EMPLOYEE_SPECIFIC_ATTENDANCE_MESSAGE));

		validateAttendanceDateMatch(foundAttendance.getAttendanceDate(), request.attendanceDate());

		foundAttendance.changeCheckOutTime(request.checkOutTime());

		attendanceRepository.save(foundAttendance);

		return CheckOutResponse.from(foundAttendance);
	}

	private void validateCheckInAttendanceExists(Long employeeId, LocalDate attendanceDate) {
		if (attendanceRepository.existsAttendance(employeeId, attendanceDate)) {
			log.error("validateCheckInAttendanceExists: already exists attendance employeeId={}, attendanceDate={}",
				employeeId, attendanceDate);
			throw new IllegalArgumentException(AttendanceConstants.ALREADY_EXISTED_CHECK_IN_MESSAGE);
		}
	}

	private void validateAttendanceDateMatch(LocalDate savedAttendanceDate, LocalDate attendanceDate) {
		if (!savedAttendanceDate.equals(attendanceDate)) {
			log.error("validateAttendanceDateMatch: mismatch attendance date savedAttendanceDate={} attendanceDate={}",
				savedAttendanceDate, attendanceDate);
			throw new IllegalArgumentException(AttendanceConstants.MISMATCHED_ATTENDANCE_DATE_MESSAGE);
		}
	}
}
