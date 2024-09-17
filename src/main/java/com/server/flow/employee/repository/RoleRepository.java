package com.server.flow.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.flow.employee.entity.Role;
import com.server.flow.employee.entity.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleType(RoleType roleType);
}
