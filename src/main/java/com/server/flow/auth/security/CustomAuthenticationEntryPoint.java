package com.server.flow.auth.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException, ServletException {
		log.error(ExceptionConstants.NOT_AUTHENTICATED_MESSAGE, authException);
		log.error(CommonConstants.REQUEST_URI_MESSAGE, request.getRequestURI());

		ApiResponse<String> apiResponse = ApiResponse.errorWithException(
			ExceptionConstants.UNAUTHORIZED_USER_MESSAGE + authException.getMessage()
		);

		String responseBody = objectMapper.writeValueAsString(apiResponse);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}
}
