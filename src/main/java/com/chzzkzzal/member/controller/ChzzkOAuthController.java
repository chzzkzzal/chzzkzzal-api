package com.chzzkzzal.member.controller;

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
		@RequestParam("state") String state
	) {
		ChzzkTokenResponse tokenResponse = chzzkAPIService.getAccessTokenFromChzzk(code, state);
		String accessToken = tokenResponse.accessToken();
		ChzzkUserResponse userInfo = chzzkAPIService.getUserInfo(accessToken);
		String jwtToken = memberService.signin(userInfo.channelId(), userInfo.channelName());

		tokenDto tokenDto = new tokenDto(tokenResponse, userInfo, jwtToken);
		return ResponseEntity.ok(tokenDto);
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
