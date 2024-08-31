package com.server.flow.employee.entity;

import java.time.LocalDate;

import com.server.flow.common.entity.BaseTimeEntity;
import com.server.flow.department.entity.Department;
import com.server.flow.employee.entity.enums.Role;
import com.server.flow.position.entity.Position;

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
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Employee extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String employeeNumber;

	@Column(nullable = false)
	private String password;

	private String email;

	private LocalDate birthDate;

	private String contact;

	private LocalDate joinDate;

	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id")
	private Position position;

	public void addEmployeeInfo(
		String encodedPassword,
		Department department,
		Position position
	) {
		this.password = encodedPassword;
		this.department = department;
		this.position = position;
	}
}
