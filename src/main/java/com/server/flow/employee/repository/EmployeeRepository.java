package com.server.flow.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.flow.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmployeeNumber(String employeeNumber);

	boolean existsByEmployeeNumber(String employeeNumber);
}
