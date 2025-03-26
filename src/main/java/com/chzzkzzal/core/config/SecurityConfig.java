package com.chzzkzzal.core.config;

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

import com.chzzkzzal.core.auth.filter.CustomOncePerRequestFilter;

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
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions.sameOrigin())  // 변경된 부분
			)
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.requestMatchers("/user").hasRole("USER")
					.requestMatchers("/admin").hasRole("ADMIN")
					.requestMatchers(
						"/**",
						"/signup"
					).permitAll()
				// .anyRequest().authenticated()
			)
			.formLogin(FormLoginConfigurer::disable)
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
