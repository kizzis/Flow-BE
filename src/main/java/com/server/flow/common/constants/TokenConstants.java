package com.server.flow.common.constants;

public class TokenConstants {
	public static final String INVALID_TOKEN_ERROR_MESSAGE = "==> Invalid JWT token. Employee Information cannot be extracted";
	public static final String EXPIRED_TOKEN_ERROR_MESSAGE = "==> Expired JWT Token : {}";
	public static final String UNSUPPORTED_TOKEN_ERROR_MESSAGE = "==> Unsupported JWT Token: {}";
	public static final String MALFORMED_TOKEN_ERROR_MESSAGE = "==> Malformed JWT Token: {}";
	public static final String INCORRECT_SIGNATURE_TOKEN_ERROR_MESSAGE = "==> Incorrect Signature JWT Token: {}";
	public static final String EMPTY__TOKEN_ERROR_MESSAGE = "==> JWT claims is empty: {}";
	public static final String GENERATED_TOKEN_MESSAGE = "==> generated JWT Token: {}";
	public static final String INVALID_ROLE_FOUND_MESSAGE = "==> Invalid role found in JWT";
	public static final String EXCLUDED_ACCESS_TOKEN_ERROR_MESSAGE = "==> Request Header does not contain access token";
}
