package com.server.flow.auth.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.flow.common.constants.CommonConstants;
import com.server.flow.common.constants.ExceptionConstants;
import com.server.flow.common.response.ApiResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException, ServletException {
		log.error(ExceptionConstants.NOT_AUTHORIZED_MESSAGE, accessDeniedException);
		log.error(CommonConstants.REQUEST_URI_MESSAGE, request.getRequestURI());

		ApiResponse<String> apiResponse = ApiResponse.errorWithException(
			ExceptionConstants.FORBIDDEN_USER_MESSAGE + accessDeniedException.getMessage()
		);

		String responseBody = objectMapper.writeValueAsString(apiResponse);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}
}
