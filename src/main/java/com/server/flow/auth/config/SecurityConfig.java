package com.server.flow.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.server.flow.auth.cookie.CookieProcessor;
import com.server.flow.auth.jwt.JWTAuthenticationFilter;
import com.server.flow.auth.jwt.JwtTokenProcessor;
import com.server.flow.auth.security.CustomAccessDeniedHandler;
import com.server.flow.auth.security.CustomAuthenticationEntryPoint;
import com.server.flow.auth.security.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProcessor jwtTokenProcessor;
	private final CookieProcessor cookieProcessor;
	private final PrincipalDetailsService principalDetailsService;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())

			.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)

			.addFilterBefore(new JWTAuthenticationFilter(
				jwtTokenProcessor,
				cookieProcessor,
				principalDetailsService
			), UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling((exceptionHandlingConfigurer) -> exceptionHandlingConfigurer
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			)

			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/api/login", "/error").permitAll()
				.requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated());
		
		return http.build();
	}
}
