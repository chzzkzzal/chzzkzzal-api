package com.chzzkzzal.core.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.chzzkzzal.core.security.infrastructure.filter.CustomOncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @EnableWebSecurity(debug = true)
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOncePerRequestFilter customOncePerRequestFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// CORS 설정: @Bean CorsConfigurationSource 있으면 자동 연동
			.cors(Customizer.withDefaults())
			// CSRF 끄기
			.csrf(csrf -> csrf.disable())
			// 세션 사용 안 함
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// H2 콘솔 프레임 보이려면 sameOrigin or disable
			.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

			.authorizeHttpRequests(auth -> auth
				// 1) H2 콘솔 전부 허용 , 근데 여기서 작용 안하는 문제 있음.
				.requestMatchers("/h2-console/**").permitAll()

				// 2) 회원가입/로그인 같은 요청도 permitAll
				.requestMatchers(HttpMethod.POST, "/auth/signup", "/auth/login").permitAll()

				// 3) 그 외 모든 요청은 일단 permitAll
				//    (실제 인증 여부는 CustomOncePerRequestFilter가 결정)
				.anyRequest().permitAll()
			)

			// 폼 로그인 사용 안 함
			.formLogin(FormLoginConfigurer::disable)

			// 커스텀 필터 등록
			.addFilterBefore(customOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/favicon.ico") // Ignore favicon requests
			.requestMatchers("/*.css", "/*.js", "/error")
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}

}
