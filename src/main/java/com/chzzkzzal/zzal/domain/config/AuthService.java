package com.chzzkzzal.zzal.domain.config;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.domain.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(String username, String password, String name) {
		Optional<Member> memberOptional = memberRepository.findByEmail(username);
		if (memberOptional.isPresent()) {
			throw new IllegalArgumentException("이미 회원가입 되어 있습니다.");
		}
		String encryptedPassword = passwordEncoder.encode(password);

		Member member = Member.builder()
			.email(username)
			.password(encryptedPassword)
			.name(name)
			.build();
		memberRepository.save(member);
	}
}
