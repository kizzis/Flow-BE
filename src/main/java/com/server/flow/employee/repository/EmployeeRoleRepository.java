package com.server.flow.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.flow.employee.entity.EmployeeRole;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
}
