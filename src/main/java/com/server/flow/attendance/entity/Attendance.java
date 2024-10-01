package com.server.flow.attendance.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.server.flow.attendance.entity.enums.AttendanceStatus;
import com.server.flow.common.entity.BaseTimeEntity;
import com.server.flow.employee.entity.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Attendance extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	private LocalDate attendanceDate;

	private LocalTime checkInTime;

	private LocalTime checkOutTime;

	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	@Column(name = "work_time")
	private Long workTime;

	@Column(name = "overtime")
	private Long overtime;

	public static Attendance of(Employee employee, LocalDate attendanceDate) {
		LocalTime checkInTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
		AttendanceStatus status = AttendanceStatus.getCheckInStatus(checkInTime);

		return Attendance.builder()
			.employee(employee)
			.attendanceDate(attendanceDate)
			.checkInTime(checkInTime)
			.status(status)
			.build();
	}

	public void finalizeCheckOut() {
		changeCheckOutTime();
		changeWorkTime();
	}

	private void changeCheckOutTime() {
		if (this.status != AttendanceStatus.VACATION || this.status != AttendanceStatus.ABSENCE) {
			LocalTime checkOutTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
			AttendanceStatus status = AttendanceStatus.getCheckOutStatus(checkOutTime);

			this.checkOutTime = checkOutTime;
			this.status = status;
		}
	}

	private void changeWorkTime() {
		if (this.checkInTime != null && this.checkOutTime != null) {
			long totalSeconds = Duration.between(this.checkInTime, this.checkOutTime).getSeconds();
			this.workTime = totalSeconds;
		}
	}
}
