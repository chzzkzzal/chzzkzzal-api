package com.chzzkzzal.core.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chzzkzzal.core.jwt.JwtProvider;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// 1) Authorization 헤더에서 'Bearer' 토큰 추출
		String token = extractBearerToken(request);

		// 2) 토큰이 유효하면 인증 객체 설정
		if (token != null && jwtProvider.validateToken(token)) {
			validateAndSetAuthentication(token);
		}

		// 다음 필터로 진행
		filterChain.doFilter(request, response);
	}

	/**
	 * Authorization 헤더에서 "Bearer " 토큰 부분만 추출
	 * @return 순수 JWT 문자열 (Bearer 제외), 없으면 null
	 */
	private String extractBearerToken(HttpServletRequest request) {
		String authHeader = request.getHeader(AUTH_HEADER);
		if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
			return authHeader.substring(7); // "Bearer " 길이=7
		}
		return null;
	}

	/**
	 * 토큰 검증 후, SecurityContextHolder에 인증 객체 저장
	 */
	private void validateAndSetAuthentication(String token) {
		// (1) channelId 추출
		String channelId = jwtProvider.getChannelIdFromToken(token);

		// (2) DB 조회
		Member member = memberRepository.findByChannelId(channelId).orElse(null);
		if (member == null) {
			return; // 회원이 없으면 인증 정보 설정 안 함
		}

		// (3) MemberUserDetails (ROLE_USER)
		MemberUserDetails principal = new MemberUserDetails(member);
		UsernamePasswordAuthenticationToken auth =
			new UsernamePasswordAuthenticationToken(
				principal,
				null,
				principal.getAuthorities() // 기본 ROLE_USER
			);

		// (4) SecurityContextHolder에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
