package com.server.flow.attendance.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.flow.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	@Query("SELECT COUNT(at) > 0 FROM Attendance at"
		+ " WHERE at.employee.id = :employeeId AND at.attendanceDate = :attendanceDate")
	boolean existsAttendance(Long employeeId, LocalDate attendanceDate);
	//
	// @Query("SELECT at FROM Attendance at"
	// 	+ " WHERE at.id = :attendanceId AND at.employee.id = :employeeId")
	// Optional<Attendance> findAttendance(Long attendanceId, Long employeeId);
}
