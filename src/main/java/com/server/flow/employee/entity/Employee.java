package com.server.flow.employee.entity;

import java.time.LocalDate;
import java.util.List;

import com.server.flow.common.entity.BaseTimeEntity;
import com.server.flow.department.entity.Department;
import com.server.flow.employee.entity.enums.RoleType;
import com.server.flow.position.entity.Position;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "employee")
	private List<EmployeeRole> employeeRoles;

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

	public void changePassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	public List<RoleType> getRoleTypes() {
		return employeeRoles.stream()
			.map(role -> role.getRole().getRoleType())
			.toList();
	}
}
