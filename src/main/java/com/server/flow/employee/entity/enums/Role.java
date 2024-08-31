package com.server.flow.employee.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
	ADMIN("ADMIN"),
	EMPLOYEE("EMPLOYEE");

	private final String value;
}
