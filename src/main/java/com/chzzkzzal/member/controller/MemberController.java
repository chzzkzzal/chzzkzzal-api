package com.chzzkzzal.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.security.MemberUserDetails;

@RestController
@RequestMapping("/member")
public class MemberController {
	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal MemberUserDetails userDetails) {

		return ResponseEntity.ok(Map.of(
			"channelId", userDetails.getMember().getChannelId(),
			"channelName", userDetails.getMember().getChannelName()
		));
	}
}
