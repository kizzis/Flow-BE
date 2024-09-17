package com.server.flow.auth.cookie;

import org.springframework.stereotype.Component;

import com.server.flow.auth.jwt.JwtProperties;
import com.server.flow.common.constants.TokenConstants;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieProcessor {
	private static final String ACCESS_COOKIE_NAME = "accessToken";
	private static final String REFRESH_COOKIE_NAME = "refreshToken";
	private final JwtProperties jwtProperties;

	public void addCookies(
		HttpServletResponse response,
		String accessToken,
		String refreshToken
	) {
		addCookie(
			response,
			ACCESS_COOKIE_NAME,
			accessToken,
			(int)jwtProperties.getAccessExpiresIn()
		);

		addCookie(
			response,
			REFRESH_COOKIE_NAME,
			refreshToken,
			(int)jwtProperties.getRefreshExpiresIn()
		);
	}

	public String extractCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ACCESS_COOKIE_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		throw new IllegalArgumentException(TokenConstants.EXCLUDED_ACCESS_TOKEN_ERROR_MESSAGE);
	}

	private void addCookie(
		HttpServletResponse response,
		String cookieName,
		String cookieValue,
		int expiry
	) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(expiry);

		response.addCookie(cookie);
	}
}
