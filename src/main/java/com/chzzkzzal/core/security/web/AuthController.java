package com.chzzkzzal.core.security.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.common.error.CustomResponse;
import com.chzzkzzal.core.security.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.member.domain.RefreshTokenService;
import com.chzzkzzal.member.dto.RefreshTokenRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "인증 API", description = "")
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

	private final RefreshTokenService RefreshTokenService;
	private final TokenProvider tokenProvider;

	@Operation(
		summary = "(개발중) 리프레쉬 토큰 발급",
		description = ""
	)
	@PostMapping("/refresh")
	public ResponseEntity<CustomResponse<String>> refresh(@RequestBody RefreshTokenRequest request) {
		// 1) 서비스 호출
		String tokenResponse = RefreshTokenService.refreshAccessToken(request.refreshToken());

		// 2) 결과 반환
		//    tokenResponse 안에 "accessToken" 만 담아도 되고,
		//    필요하다면 추가 필드를 넣을 수 있음
		if (tokenResponse == null) {
			throw new IllegalArgumentException("Invalid or Expired Refresh Token");
		}
		return CustomResponse.okResponseEntity(tokenResponse);
	}

	@Operation(
		summary = "로그인 체크",
		description = "### 로그인 했는지 체크한다.(By http-only) \n"
	)
	@GetMapping("/auth/check")
	public ResponseEntity<CustomResponse<LoginCheckResponse>> checkAuth(HttpServletRequest request) {
		// 1. 요청에서 쿠키 추출
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SESSION".equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}

		// 2. 토큰 검증
		boolean isAuthenticated = false;
		System.out.println(jwtToken);
		if (jwtToken != null) {
			isAuthenticated = tokenProvider.validateToken(jwtToken); // JWT 검증 로직
		}
		System.out.println("로그인 여부 확인");
		System.out.println(isAuthenticated);
		LoginCheckResponse response = new LoginCheckResponse(isAuthenticated);
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "쿠키제거로 로그아웃 (By http-only)", description = ""
	)
	@PostMapping("/auth/logout")
	public ResponseEntity<CustomResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		// 쿠키 제거 (예: SESSION 쿠키)
		Cookie cookie = new Cookie("SESSION", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return CustomResponse.okResponseEntity();
	}

	record LoginCheckResponse(Boolean loggedIn) {
	}
}
