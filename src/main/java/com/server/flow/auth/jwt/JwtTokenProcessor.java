package com.server.flow.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.server.flow.auth.jwt.dto.JwtPayloadResponse;
import com.server.flow.common.constants.AuthConstants;
import com.server.flow.employee.entity.Employee;
import com.server.flow.employee.entity.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProcessor {
	private final JwtProperties jwtProperties;

	public String generateAccessToken(Employee employee) {
		String jwt = generateToken(employee, TokenType.ACCESS);
		log.info("==> generated access token: {}", jwt);
		return jwt;
	}

	public String generateRefreshToken(Employee employee) {
		String jwt = generateToken(employee, TokenType.ACCESS);
		log.info("==> generated refresh token: {}", jwt);
		return jwt;
	}

	public JwtPayloadResponse extractUserInfo(String jwtToken) {
		if (jwtToken != null && !jwtToken.isBlank() && verifyToken(jwtToken)) {
			return extractPayload(jwtToken);
		}
		throw new IllegalArgumentException("==> Invalid JWT token. Employee Information cannot be extracted");
	}

	public boolean verifyToken(String token) {
		try {
			getJwtParser().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.info("==> Expired JWT Token : {}", token, e);
		} catch (UnsupportedJwtException e) {
			log.info("==> Unsupported JWT Token: {}", token, e);
		} catch (MalformedJwtException e) {
			log.info("==> Malformed JWT Token: {}", token, e);
		} catch (SignatureException e) {
			log.info("==> Incorrect Signature JWT Token: {}", token, e);
		} catch (IllegalArgumentException e) {
			log.info("==> JWT claims is empty: {}", token, e);
		}
		return false;
	}

	private SecretKeySpec getSecretKeySpec(TokenType tokenType) {
		String secretKey =
			tokenType == TokenType.ACCESS ? jwtProperties.getAccessSecretKey() : jwtProperties.getRefreshSecretKey();
		byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
	}

	private String generateToken(Employee employee, TokenType tokenType) {
		Date now = new Date();
		SecretKeySpec secretKeySpec = getSecretKeySpec(tokenType);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpiresIn()))
			.claim(AuthConstants.EMPLOYEE_ID, employee.getId())
			.claim(AuthConstants.EMPLOYEE_NUMBER, employee.getEmployeeNumber())
			.claim(AuthConstants.ROLE, employee.getRole())
			.signWith(secretKeySpec, SignatureAlgorithm.HS256)
			.compact();
	}

	private JwtParser getJwtParser() {
		return Jwts.parserBuilder()
			.setSigningKey(getSecretKeySpec(TokenType.ACCESS))
			.build();
	}

	private JwtPayloadResponse extractPayload(String jwtToken) {
		Claims claims = getJwtParser()
			.parseClaimsJws(jwtToken)
			.getBody();

		Long employeeId = claims.get(AuthConstants.EMPLOYEE_ID, Long.class);
		String employeeNumber = claims.get("employeeNumber", String.class);
		String roleType = claims.get(AuthConstants.ROLE, String.class);
		Role role = Role.valueOf(roleType);

		return new JwtPayloadResponse(employeeId, employeeNumber, role);
	}

	public enum TokenType {
		ACCESS, REFRESH
	}
}
