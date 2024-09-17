package com.server.flow.auth.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.flow.auth.security.dto.EmployeePrincipalDetails;
import com.server.flow.common.constants.EmployeeConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.entity.enums.RoleType;
import com.server.flow.employee.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	private final EmployeeRepository employeeRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
		return employeeRepository.findByEmployeeNumber(employeeNumber)
			.map(employee -> new EmployeePrincipalDetails(
				employee.getId(),
				employee.getEmployeeNumber(),
				employee.getPassword(),
				getRoleTypes(employee)
			))
			.map(PrincipalDetails::new)
			.orElseThrow(
				() -> new UsernameNotFoundException(
					EmployeeConstants.EMPLOYEE_NOT_FOUND_WITH_EMPLOYEENUMBER_MESSAGE + employeeNumber));
	}

	private List<RoleType> getRoleTypes(Employee employee) {
		return employee.getEmployeeRoles().stream()
			.map(role -> role.getRole().getRoleType())
			.toList();
	}
}
