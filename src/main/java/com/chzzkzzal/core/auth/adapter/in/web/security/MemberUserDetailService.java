package com.chzzkzzal.core.auth.adapter.in.web.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.out.LoadMemberPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
	private final LoadMemberPort loadMemberPort;

	@Override
	public MemberUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		return new MemberUserDetails(loadMemberPort.loadMemberEntity(Long.valueOf(id)));
	}
}
