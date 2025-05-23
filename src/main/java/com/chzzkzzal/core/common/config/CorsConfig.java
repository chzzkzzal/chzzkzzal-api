package com.chzzkzzal.core.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.chzzkzzal.core.common.properties.FrontProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

	private final FrontProperties frontProperties;

	@Bean
	public CorsFilter corsFilter() {
		String frontDomain = frontProperties.domain();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
		config.addAllowedOriginPattern(frontDomain);
		// config.addAllowedOrigin(FRONT_DOMAIN); // 모든 ip에 응답 허용
		config.addAllowedHeader("*"); // 모든 헤더에 응답 허용
		config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청 허용
		source.registerCorsConfiguration("/**", config); // 모든 경로에 위 설정 적용
		return new CorsFilter(source);
	}
}
