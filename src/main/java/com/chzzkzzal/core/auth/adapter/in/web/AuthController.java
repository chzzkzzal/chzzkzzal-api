package com.chzzkzzal.core.auth.adapter.in.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.adapter.in.facade.AuthFacade;
import com.chzzkzzal.core.auth.adapter.in.web.exception.MissingJwtTokenException;
import com.chzzkzzal.core.auth.adapter.out.web.TokenResolver;
import com.chzzkzzal.core.auth.application.command.LoginCheckCommand;
import com.chzzkzzal.core.auth.application.command.LogoutCommand;
import com.chzzkzzal.core.auth.application.command.ReissueTokenCommand;
import com.chzzkzzal.core.auth.application.port.out.CookieWriterPort;
import com.chzzkzzal.core.auth.application.result.LoginCheckResponse;
import com.chzzkzzal.core.auth.application.result.ReissueTokensResponse;
import com.chzzkzzal.core.auth.application.result.TokenResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "인증 API", description = "")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;
	private final TokenResolver tokenResolver;
	private final CookieWriterPort cookieWriterPort;
	private final TokenProperties tokenProperties;

	@Operation(summary = "액세스 토큰 재발급 API", description = "액세스 토큰 재발급 API [담당자 : 김진수]")
	@PostMapping("/reissue")
	public ResponseEntity<CustomResponse<ReissueTokensResponse>>
	reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String jwtToken = tokenResolver
			.resolveFromCookie(request, REFRESH_TOKEN.name())
			.orElseThrow(MissingJwtTokenException::new);

		ReissueTokenCommand command = new ReissueTokenCommand(jwtToken);

		ReissueTokensResponse reissueTokensResponse = authFacade.reissue(command);

		cookieWriterPort.injectRefreshTokenToCookie(new TokenResult(reissueTokensResponse.externalId(),
			reissueTokensResponse.refreshToken()), response);

		cookieWriterPort.addCookie(SESSION.name(), reissueTokensResponse.accessToken(), tokenProperties.expirationTime()
			.accessTokenDuration(), response);

		return CustomResponse.okResponseEntity(reissueTokensResponse);
	}

	@Operation(summary = "로그아웃 API", description = "로그아웃 API [담당자 : 김진수]")
	@PostMapping("/logout")
	public ResponseEntity<CustomResponse<Void>>
	logout(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = tokenResolver
			.resolveRefreshTokenFromRequest(request)
			.orElseThrow(MissingJwtTokenException::new);

		LogoutCommand command = new LogoutCommand(refreshToken);
		authFacade.logout(command);
		cookieWriterPort.invalidateCookie(SESSION.name(), response);
		cookieWriterPort.invalidateCookie(REFRESH_TOKEN.name(), response);

		return CustomResponse.okResponseEntity();
	}

	@Operation(summary = "로그인 체크", description = "로그인 상태 체크 API [담당자 : 김진수]")
	@GetMapping("/check")
	public ResponseEntity<CustomResponse<LoginCheckResponse>>
	checkAuth(HttpServletRequest request) {
		String jwtToken = tokenResolver
			.resolveFromCookie(request, SESSION.name())
			.orElseThrow(MissingJwtTokenException::new);

		LoginCheckCommand command = new LoginCheckCommand(jwtToken);
		return CustomResponse.okResponseEntity(authFacade.checkLoginStatus(command));
	}

	//	private void logSuccessfulReissue(String ip) {
	//		log.info("User {} accessed from IP {} and successfully reissued a token", maskId(externalId), ip);
	//	}
	//
	//	private String maskId(String id) {
	//		if (id == null || id.length() < 4)
	//			return "****";
	//		return id.substring(0, 4) + "****";
	//	}
}
