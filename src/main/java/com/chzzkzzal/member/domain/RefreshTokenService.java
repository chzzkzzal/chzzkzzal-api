package com.chzzkzzal.member.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.auth.exception.RefreshTokenInvalidException;
import com.chzzkzzal.core.auth.jwt.TokenProvider;
import com.chzzkzzal.core.auth.jwt.TokenResult;
import com.chzzkzzal.member.dto.TokenResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
