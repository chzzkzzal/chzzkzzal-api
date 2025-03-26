package com.chzzkzzal.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.jwt.JwtProvider;
import com.chzzkzzal.member.domain.refreshJwtTokenService;
import com.chzzkzzal.member.dto.RefreshTokenRequest;
import com.chzzkzzal.member.dto.TokenResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

	private final refreshJwtTokenService refreshJwtTokenService;
	private final JwtProvider jwtProvider;

	/**
	 * Refresh Token 이용하여 새 Access Token 발급
	 */
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
		// 1) 서비스 호출
		TokenResponse tokenResponse = refreshJwtTokenService.refreshAccessToken(request.refreshToken());

		// 2) 결과 반환
		//    tokenResponse 안에 "accessToken" 만 담아도 되고,
		//    필요하다면 추가 필드를 넣을 수 있음
		if (tokenResponse == null) {
			return ResponseEntity.status(401).body("Invalid or Expired Refresh Token");
		}
		return ResponseEntity.ok(tokenResponse);
	}
	@GetMapping("/api/check-auth")
	public ResponseEntity<Map<String, Boolean>> checkAuth(HttpServletRequest request) {
		// 1. 요청에서 쿠키 추출
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}

		// 2. 토큰 검증
		boolean isAuthenticated = false;
		System.out.println(jwtToken);
		if (jwtToken != null) {
			isAuthenticated = jwtProvider.validateToken(jwtToken); // JWT 검증 로직
		}
		System.out.println("로그인 여부 확인");
		System.out.println(isAuthenticated);
		return ResponseEntity.ok(Map.of("isAuthenticated", isAuthenticated));
	}
}
