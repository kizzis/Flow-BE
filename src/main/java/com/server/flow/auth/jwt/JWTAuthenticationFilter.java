package com.server.flow.auth.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.server.flow.auth.cookie.CookieProcessor;
import com.server.flow.auth.jwt.dto.JwtPayloadResponse;
import com.server.flow.auth.security.PrincipalDetailsService;
import com.server.flow.common.constants.AuthConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private static final String[] WHITELIST = {
		"/css/*",
		"/js/*",
		"/images/*",
		"/api/login"
	};
	private final JwtTokenProcessor jwtTokenProcessor;
	private final CookieProcessor cookieProcessor;
	private final PrincipalDetailsService principalDetailsService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		try {
			System.out.println("isMatchedWhiteList Start");
			if (isMatchedWhiteList(request.getRequestURI())) {
				filterChain.doFilter(request, response);
				return;
			}
			System.out.println("isMatchedWhiteList End");

			System.out.println("Options method Start");
			if (request.getMethod().equalsIgnoreCase(AuthConstants.HTTP_OPTIONS_METHOD)) {
				filterChain.doFilter(request, response);
				return;
			}
			System.out.println("Options method End");

			System.out.println("JWTAuthenticationFilter.doFilterInternal extractCookie Start");
			String jwtToken = cookieProcessor.extractCookie(request);
			System.out.println("JWTAuthenticationFilter.doFilterInternal extractCookie End");
			System.out.println("JWTAuthenticationFilter.doFilterInternal extractUsersInfo Start");
			JwtPayloadResponse jwtPayloadResponse = jwtTokenProcessor.extractUserInfo(jwtToken);
			System.out.println("JWTAuthenticationFilter.doFilterInternal extractUsersInfo End");

			System.out.println("JWTAuthenticationFilter.doFilterInternal loadUserByUsername Start");
			UserDetails userDetails = principalDetailsService.loadUserByUsername(jwtPayloadResponse.employeeNumber());
			System.out.println("JWTAuthenticationFilter.doFilterInternal loadUserByUsername End");

			System.out.println("JWTAuthenticationFilter.doFilterInternal UsernamePasswordAuthenticationToken  Start");
			if (userDetails != null) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			System.out.println("JWTAuthenticationFilter.doFilterInternal UsernamePasswordAuthenticationToken  End");

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			handleException(response, e.getMessage());
		}
	}

	private boolean isMatchedWhiteList(String requestURI) {
		return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
	}

	private void handleException(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType("application/json");
		response.getWriter().write("filter_error: " + message);
	}
}
