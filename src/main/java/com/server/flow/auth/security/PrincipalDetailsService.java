package com.server.flow.auth.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	private final EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
		Optional<Employee> employee = employeeRepository.findByEmployeeNumber(employeeNumber);

		if (employee.isPresent()) {
			return new PrincipalDetails(employee.get());
		}
		throw new UsernameNotFoundException(employeeNumber);
	}
}
