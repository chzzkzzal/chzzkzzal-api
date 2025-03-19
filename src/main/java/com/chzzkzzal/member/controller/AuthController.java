package com.chzzkzzal.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.member.domain.AuthService;
import com.chzzkzzal.member.dto.RefreshTokenRequest;
import com.chzzkzzal.member.dto.TokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * Refresh Token 이용하여 새 Access Token 발급
	 */
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
		// 1) 서비스 호출
		TokenResponse tokenResponse = authService.refreshAccessToken(request.refreshToken());

		// 2) 결과 반환
		//    tokenResponse 안에 "accessToken" 만 담아도 되고,
		//    필요하다면 추가 필드를 넣을 수 있음
		if (tokenResponse == null) {
			return ResponseEntity.status(401).body("Invalid or Expired Refresh Token");
		}
		return ResponseEntity.ok(tokenResponse);
	}

}
