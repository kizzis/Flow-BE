package com.server.flow.auth.jwt;

import java.io.IOException;

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
		if (isMatchedWhiteList(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (request.getMethod().equalsIgnoreCase(AuthConstants.HTTP_OPTIONS_METHOD)) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwtToken = cookieProcessor.extractCookie(request);
		JwtPayloadResponse jwtPayloadResponse = jwtTokenProcessor.extractUserInfo(jwtToken);

		UserDetails userDetails = principalDetailsService.loadUserByUsername(jwtPayloadResponse.employeeNumber());

		if (userDetails != null) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);
	}

	private boolean isMatchedWhiteList(String requestURI) {
		return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
	}
}
