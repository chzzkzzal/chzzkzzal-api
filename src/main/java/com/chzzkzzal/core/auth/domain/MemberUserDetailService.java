package com.chzzkzzal.core.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String channelId) throws UsernameNotFoundException {
		return memberRepository.findByChannelId(channelId)
			.map(MemberUserDetails::new)
			.orElseThrow(() -> new UsernameNotFoundException("Not found: " + channelId));
	}
}
