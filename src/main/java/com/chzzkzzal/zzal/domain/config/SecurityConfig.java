package com.chzzkzzal.zzal.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.chzzkzzal.zzal.domain.config.loginFilter.OAuth2LoginAuthenticationFilter;
import com.chzzkzzal.zzal.domain.config.loginfailhandler.Http401Handler;
import com.chzzkzzal.zzal.domain.config.loginfailhandler.Http403Handler;
import com.chzzkzzal.zzal.domain.config.loginfailhandler.LoginFailureHandler;
import com.chzzkzzal.zzal.domain.config.loginsuccesshandler.LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @EnableWebSecurity(debug = true)
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
	private final ObjectMapper objectMapper;
	private final UserDetailsService userDetailsService;
	private final LoginSuccessHandler loginSuccessHandler;
	private final LoginFailureHandler loginFailureHandler;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/favicon.ico") // Ignore favicon requests
			.requestMatchers("/*.css", "/*.js", "/error")
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions.sameOrigin())  // 변경된 부분
			)
			// .sessionManagement(session ->
			// 	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.requestMatchers("/user").hasRole("USER")
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers(
					"/**",
					"/signup",
					"/api/oauth/**",
					"/oauth/google",
					"/oauth/kakao",
					"/login/**",
					"/oauth2/**"
				).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(FormLoginConfigurer::disable)
			.exceptionHandling(e -> {
				e.accessDeniedHandler(new Http403Handler(objectMapper));
				e.authenticationEntryPoint(new Http401Handler(objectMapper));
			})
			// .sessionManagement(session ->
			// 	session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			// )
			.addFilterBefore(oauth2LoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.rememberMe(rm -> rm.rememberMeParameter("remember")
				.alwaysRemember(false)
				.tokenValiditySeconds(2592000));// 로그인 기억 30일 설정
		// .oauth2Login(oauth2 -> oauth2
		// 	.userInfoEndpoint(userInfoEndpoint ->
		// 		userInfoEndpoint.userService(customOAuth2UserService))
		// 	.successHandler(oAuth2AuthenticationSuccessHandler)
		// )

		return http.build();
	}

	@Bean
	public OAuth2LoginAuthenticationFilter oauth2LoginAuthenticationFilter() {
		OAuth2LoginAuthenticationFilter filter = new OAuth2LoginAuthenticationFilter(
			objectMapper, loginSuccessHandler, loginFailureHandler);
		filter.setAuthenticationManager(authenticationManager());

		//세션 발급 해주기
		filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
		return filter;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);

		return new ProviderManager(provider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
