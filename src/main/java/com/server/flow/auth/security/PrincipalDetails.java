package com.server.flow.auth.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.server.flow.auth.security.dto.EmployeePrincipalDetails;
import com.server.flow.employee.entity.enums.RoleType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
	private final EmployeePrincipalDetails employee;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		employee.roleTypes().forEach(roleType ->
			authorities.add(new SimpleGrantedAuthority(roleType.getValue())));
		return authorities;
	}

	@Override
	public String getPassword() {
		return employee.password();
	}

	@Override
	public String getUsername() {
		return employee.username();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getEmployeeId() {
		return employee.employeeId();
	}

	public List<RoleType> getRoleTypes() {
		return employee.roleTypes();
	}
}
