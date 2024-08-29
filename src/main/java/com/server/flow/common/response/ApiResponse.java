package com.server.flow.common.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.server.flow.common.enums.ResponseStatus;

public record ApiResponse<T>(
	ResponseStatus status,
	String message,
	T data
) {
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(ResponseStatus.SUCCESS, message, data);
	}

	public static <T> ApiResponse<T> successWithNoContent(String message) {
		return new ApiResponse<>(ResponseStatus.SUCCESS, message, null);
	}

	public static ApiResponse<String> failWithNonValidated(BindingResult bindingResult) {
		List<ObjectError> allErrors = bindingResult.getAllErrors();

		String errorMessages = allErrors.stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(", "));

		return new ApiResponse<>(ResponseStatus.FAIL, "validation failure", errorMessages);
	}

	public static ApiResponse<String> errorWithException(String message) {
		return new ApiResponse<>(ResponseStatus.ERROR, message, "None");
	}
}
