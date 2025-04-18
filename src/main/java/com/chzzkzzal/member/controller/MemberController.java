package com.chzzkzzal.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.security.domain.MemberUserDetails;
import com.chzzkzzal.member.domain.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "(미구현) 회원 API", description = "")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal MemberUserDetails userDetails) {

		return ResponseEntity.ok(Map.of(
			"channelId", userDetails.getMember().getChannelId(),
			"channelName", userDetails.getMember().getChannelName()
		));
	}

	@PostMapping("mock")
	public Long createMockMember() {
		return memberService.findOrCreate("채널Id", "채널이름").getId();
	}
}
