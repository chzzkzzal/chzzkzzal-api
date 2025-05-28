package com.chzzkzzal.core.auth.adapter.in.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

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

import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.adapter.in.facade.AuthFacade;
import com.chzzkzzal.core.auth.application.command.SignInCommand;
import com.chzzkzzal.core.auth.application.port.out.CookieWriterPort;
import com.chzzkzzal.core.auth.application.result.SignInResponse;
import com.chzzkzzal.core.chzzk.adapter.in.facade.ChzzkDevelopersFacade;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "치지직 Auth API", description = "### 치치직 애플리케이션 API 사용 : "
	+ "https://developers.chzzk.naver.com/application")
public class ChzzkOAuthController {

	@Value("${chzzkzzal.front}")
	private String redirectUrl;

	private final ChzzkDevelopersFacade chzzkDevelopersFacade;
	private final AuthFacade authFacade;
	private final CookieWriterPort cookieWriterPort;
	private final TokenProperties tokenProperties;

	@Operation(summary = "치지직 로그인", description = "치지직 로그인 API [담당자 : 김진수]")
	@GetMapping("${chzzk.oauth.redirection-url}")
	public ResponseEntity<?>
	callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state
	) {
		ChzzkTokenResponse chzzkToken = chzzkDevelopersFacade.issueAccessToken(code, state);
		ChzzkUserResponse chzzkUserResponse = chzzkDevelopersFacade.getUserChannelInfo(chzzkToken.accessToken());

		SignInResponse result = authFacade.signIn(new SignInCommand(
			chzzkUserResponse.channelId(),
			chzzkUserResponse.channelName()
		));

		ResponseCookie accessCookie = cookieWriterPort.makeCookie(
			SESSION.name(),
			result.accessToken(),
			Duration.ofMinutes(tokenProperties.expirationTime().accessTokenMinutes())
		);
		ResponseCookie refreshCookie = cookieWriterPort.makeCookie(
			REFRESH_TOKEN.name(),
			result.refreshToken(),
			Duration.ofHours(tokenProperties.expirationTime().refreshTokenHours())
		);

		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create(redirectUrl))
			.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
			.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
			.build();
	}
}
