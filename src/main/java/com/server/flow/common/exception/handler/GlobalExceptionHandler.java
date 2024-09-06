package com.server.flow.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.server.flow.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiResponse.errorWithException(e.getMessage());
	}
}
