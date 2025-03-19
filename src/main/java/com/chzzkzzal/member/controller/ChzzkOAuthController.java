package com.chzzkzzal.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.member.domain.ChzzkAuthService;
import com.chzzkzzal.member.dto.ChzzkRevokeRequest;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChzzkOAuthController {

	private final ChzzkAuthService chzzkAuthService;

	@GetMapping("${chzzk.oauth.redirection-url}")
	public ResponseEntity<?> callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state
	) {
		ChzzkTokenResponse tokenResponse = chzzkAuthService.getAccessTokenFromChzzk(code, state);
		String accessToken = tokenResponse.accessToken();
		ChzzkUserResponse userInfo = chzzkAuthService.getUserInfo(accessToken);
		String jwtToken = chzzkAuthService.signin(userInfo.channelId(), userInfo.channelName());

		tokenDto tokenDto = new tokenDto(tokenResponse, userInfo, jwtToken);
		return ResponseEntity.ok(tokenDto);
	}

	@PostMapping("/revoke")
	public ResponseEntity<String> revokeToken(@RequestBody ChzzkRevokeRequest request) {
		String revokeResult = chzzkAuthService.revokeToken(request.getToken(), request.getTokenTypeHint());
		return ResponseEntity.ok(revokeResult);
	}

	static record tokenDto(
		ChzzkTokenResponse chzzkTokenResponse,
		ChzzkUserResponse chzzkUserResponse,
		String jwtToken
	) {

	}

}
