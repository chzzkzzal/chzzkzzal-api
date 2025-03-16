package com.chzzkzzal.zzal.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions.sameOrigin())  // 변경된 부분
			)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/**",
					"/api/oauth/**",
					"/oauth/google",
					"/oauth/kakao",
					"/login/**",
					"/oauth2/**",
					"/h2-console/**",
					"/favicon.ico",     // 파비콘
					"/*.css",           // CSS 파일
					"/*.js"            // JavaScript 파일
				).permitAll()
				.anyRequest().authenticated()
			);
		// .oauth2Login(oauth2 -> oauth2
		// 	.userInfoEndpoint(userInfoEndpoint ->
		// 		userInfoEndpoint.userService(customOAuth2UserService))
		// 	.successHandler(oAuth2AuthenticationSuccessHandler)
		// )

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
