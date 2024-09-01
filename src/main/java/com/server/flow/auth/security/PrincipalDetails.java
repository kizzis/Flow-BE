package com.server.flow.auth.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.server.flow.common.constants.AuthConstants;
import com.server.flow.employee.entity.Employee;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
	private final Employee employee;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add((GrantedAuthority)() -> AuthConstants.ROLE_ + employee.getRole().getValue());
		return collect;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getEmployeeNumber();
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
		return employee.getId();
	}
}
