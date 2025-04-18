package com.chzzkzzal.core.security.web;

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

import com.chzzkzzal.core.external.chzzk.intrastructure.http.auth.ChzzkRevokeClient;
import com.chzzkzzal.core.external.chzzk.intrastructure.http.auth.ChzzkTokenClient;
import com.chzzkzzal.core.external.chzzk.intrastructure.http.user.ChzzkUserClient;
import com.chzzkzzal.member.domain.MemberService;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "치지직 Auth API", description = "### 치치직 애플리케이션 API 사용 : "
	+ "https://developers.chzzk.naver.com/application")
public class ChzzkOAuthController {
	@Value("${chzzkzzal.front}")
	private String FRONT_DOMAIN;

	@Value("${cookie.name}")
	private String COOKIE_NAME;

	@Value("${cookie.domain}")
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

	@Operation(
		summary = "치지직 AccessToken 발급 및 로그인",
		description =
			"### 로그인 프로세스\n" +
				"1. 유저가 치지직 동의화면 모달창으로부터 동의하기 누르면 치지직 애플리케이션에 등록한 redirectUrl로 code, state와 함께 리다이렉트\n" +
				"2. 백엔드에서 code, state 정보로 AccessToken 발급\n" +
				"3. 첫 로그인시 회원가입 동작\n" +
				"4. 프론트 홈화면으로 리다이렉트 및 JWT 토큰 발급"
	)
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
