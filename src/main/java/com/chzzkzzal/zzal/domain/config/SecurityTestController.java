package com.chzzkzzal.zzal.domain.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.zzal.domain.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SecurityTestController {
	private final MemberRepository memberRepository;
	private final CustomUserDetailsService userDetailsService;

	@GetMapping()
	public String test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		String message = userPrincipal.getMemberId() + "," + userPrincipal.getUsername();
		return "테스트 페이지입니다. " + message;
	}

	@GetMapping("/signup")
	public String signup(HttpServletRequest request) {
		// 1) 새 회원 생성 및 DB 저장
		Member member = new Member("testName", "testEmail", "testPassword");
		memberRepository.save(member);

		// 2) 방금 저장한 회원으로부터 UserDetails 생성
		//    (이미 존재하는 userDetailsService를 재사용하는 것이 안전)
		UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());

		// 3) Authentication 객체 생성
		UsernamePasswordAuthenticationToken authToken =
			new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
			);

		// 4) SecurityContext에 등록 (로그인 처리)
		SecurityContextHolder.getContext().setAuthentication(authToken);

		// 5) 세션 정책이 STATELESS가 아닐 경우, 세션에도 반영
		HttpSession session = request.getSession(true);
		session.setAttribute(
			HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
			SecurityContextHolder.getContext()
		);

		return "회원가입 테스트 멤버 생성 완료 (자동 로그인)";
	}
}
