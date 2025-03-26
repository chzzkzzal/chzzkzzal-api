package com.chzzkzzal.member.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.client.ChzzkAPIService;
import com.chzzkzzal.member.domain.MemberService;
import com.chzzkzzal.member.dto.ChzzkRevokeRequest;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChzzkOAuthController {

	private final ChzzkAPIService chzzkAPIService;
	private final MemberService memberService;

	@GetMapping("${chzzk.oauth.redirection-url}")
	public ResponseEntity<?> callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state,
		HttpServletResponse response
	) {
		ChzzkTokenResponse tokenResponse = chzzkAPIService.getAccessTokenFromChzzk(code, state);
		String accessToken = tokenResponse.accessToken();
		ChzzkUserResponse userInfo = chzzkAPIService.getUserInfo(accessToken);
		String jwtToken = memberService.signin(userInfo.channelId(), userInfo.channelName());

		// JWT 토큰을 HTTP-only 쿠키로 설정
		ResponseCookie cookie = ResponseCookie.from("jwtToken", jwtToken)
			.httpOnly(true)
			.secure(false) // 로컬호스트에서는 false, 프로덕션에서는 true로 설정
			.path("/")
			.maxAge(7 * 24 * 60 * 60) // 7일 유효기간 (초 단위)
			.sameSite("Lax") // CSRF 보호
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		// 리다이렉트 응답 생성
		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create("http://localhost:3000"))
			.build();
	}

	@PostMapping("/revoke")
	public ResponseEntity<String> revokeToken(@RequestBody ChzzkRevokeRequest request) {
		String revokeResult = chzzkAPIService.revokeToken(request.getToken(), request.getTokenTypeHint());
		return ResponseEntity.ok(revokeResult);
	}

	record tokenDto(
		ChzzkTokenResponse chzzkTokenResponse,
		ChzzkUserResponse chzzkUserResponse,
		String jwtToken
	) {

	}

}
