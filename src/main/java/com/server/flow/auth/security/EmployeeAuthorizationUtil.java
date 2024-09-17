package com.server.flow.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.server.flow.auth.security.dto.EmployeeBasicInfo;

public class EmployeeAuthorizationUtil {

	public static Long getLoginEmployeeId() {
		PrincipalDetails principal = getPrincipal();
		return principal.getEmployeeId();
	}

	public static EmployeeBasicInfo getEmployeeBasicInfo() {
		PrincipalDetails principal = getPrincipal();
		return EmployeeBasicInfo.from(principal);
	}

	private static PrincipalDetails getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (PrincipalDetails)authentication.getPrincipal();
	}
}
