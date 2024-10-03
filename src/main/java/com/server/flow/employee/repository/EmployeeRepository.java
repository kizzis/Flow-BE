package com.server.flow.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.flow.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmployeeNumber(String employeeNumber);

	@Query("SELECT em FROM Employee em WHERE em.name LIKE CONCAT('%', :name, '%')")
	Optional<List<Employee>> findByNameEmployees(String name);

	boolean existsByEmployeeNumber(String employeeNumber);
}
