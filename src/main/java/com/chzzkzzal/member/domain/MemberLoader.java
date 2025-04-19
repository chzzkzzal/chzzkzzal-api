package com.chzzkzzal.member.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberLoader {
	private final MemberRepository memberRepository;

	public Member loadMember(Long memberId){
		return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}
}
