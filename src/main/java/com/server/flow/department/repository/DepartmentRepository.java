package com.server.flow.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.flow.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
