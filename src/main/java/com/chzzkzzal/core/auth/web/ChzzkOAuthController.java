package com.chzzkzzal.core.auth.web;

import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.client.facade.ChzzkRevokeClient;
import com.chzzkzzal.core.client.facade.ChzzkTokenClient;
import com.chzzkzzal.core.client.facade.ChzzkUserClient;
import com.chzzkzzal.member.domain.MemberService;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChzzkOAuthController {
	@Value("${chzzkzzal.front.domain}")
	private String FRONT_DOMAIN;

	@Value("${cookie.name}")
	private String COOKIE_NAME;

	@Value("${cookie.to-domain}")
	private String COOKIE_DOMAIN;

	@Value("${cookie.path}")
	private String COOKIE_PATH;
	@Value("${cookie.days}")
	private int COOKIE_DAYS;
	@Value("${cookie.same_site}")
	private String COOKIE_SAME_SITE;

	private final ChzzkTokenClient chzzkTokenClient;
	private final ChzzkUserClient chzzkUserClient;
	private final ChzzkRevokeClient chzzkRevokeClient;
	private final MemberService memberService;

	@GetMapping("${chzzk.oauth.redirection-url}")
	public ResponseEntity<?> callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state,
		HttpServletResponse response
	) {
		ChzzkTokenResponse tokenResponse = chzzkTokenClient.fetchAccessToken(code, state);
		String accessToken = tokenResponse.accessToken();
		ChzzkUserResponse userInfo = chzzkUserClient.fetchUserInfo(accessToken);

		String jwtToken = memberService.signin(userInfo.channelId(), userInfo.channelName());

		// JWT 토큰을 HTTP-only 쿠키로 설정
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwtToken)
			.domain(COOKIE_DOMAIN)
			.httpOnly(true)
			.secure(true) // 로컬호스트에서는 false, 프로덕션에서는 true로 설정
			.path(COOKIE_PATH)
			.maxAge(Duration.ofDays(COOKIE_DAYS))
			.sameSite(COOKIE_SAME_SITE) // CSRF 보호
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		return ResponseEntity.status(HttpStatus.FOUND)
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.location(URI.create(FRONT_DOMAIN))
			.build();
	}

	// @PostMapping("/revoke")
	// public ResponseEntity<String> revokeToken(@RequestBody ChzzkRevokeRequest request) {
	// 	String revokeResult = chzzkRevokeClient.revokeToken(request.getToken(), request.getTokenTypeHint());
	// 	return ResponseEntity.ok(revokeResult);
	// }

	record tokenDto(
		ChzzkTokenResponse chzzkTokenResponse,
		ChzzkUserResponse chzzkUserResponse,
		String jwtToken
	) {

	}

}
