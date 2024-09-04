package com.server.flow.department.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.department.entity.Department;
import com.server.flow.department.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentAddService {
	private final DepartmentRepository departmentRepository;

	public Department getAndCreateDepartment(String departmentName) {
		Department department = Department.from(departmentName);
		return departmentRepository.save(department);
	}
}
