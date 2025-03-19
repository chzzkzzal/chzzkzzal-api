package com.chzzkzzal.member.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 신규 회원 생성
	 */

	public Member findOrCreate(String channelId, String channelName) {

		return memberRepository.findByChannelId(channelId)
			.orElseGet(() -> {
				return createMember(channelId, channelName);
			});
	}

	private Member createMember(String channelId, String channelName) {
		String encryptedPassword = passwordEncoder.encode(channelId);

		Member member = Member.builder()
			.channelId(channelId)
			.password(encryptedPassword)
			.channelName(channelName)
			.build();

		return memberRepository.save(member);
	}

}
