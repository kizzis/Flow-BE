package com.server.flow.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EmployeeAuthorizationUtil {

	public static Long getLoginEmployeeId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
		return principal.getEmployeeId();
	}
}
