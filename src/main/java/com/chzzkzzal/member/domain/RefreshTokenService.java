package com.chzzkzzal.member.domain;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.security.infrastructure.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final TokenProvider tokenProvider;
	private final MemberRepository memberRepository;

	/**
	 * 1) Refresh Token 검증
	 * 2) channelId 추출
	 * 3) DB 조회
	 * 4) 새 Access Token 발급
	 */
	public String refreshAccessToken(String refreshToken) {
		// (1) Refresh Token 검증
		if (!tokenProvider.validateToken(refreshToken)) {
			log.debug("Invalid Refresh Token: {}", refreshToken);
			return null; // or throw custom exception
		}

		// (2) channelId 추출
		String channelId = "1";

		// (3) DB 조회
		Member member = memberRepository.findByChannelId(channelId).orElse(null);
		if (member == null) {
			log.debug("No member found for channelId={}", channelId);
			return null; // or throw custom exception
		}

		// (4) 새 Access Token 발급
		String newAccessJwt = tokenProvider.createAccessToken(channelId);

		// 응답 DTO 반환
		return newAccessJwt;
	}

}
