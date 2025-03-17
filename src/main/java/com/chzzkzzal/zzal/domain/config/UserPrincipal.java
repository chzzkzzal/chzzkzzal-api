package com.chzzkzzal.zzal.domain.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.chzzkzzal.zzal.domain.model.Member;

public class UserPrincipal extends User {
	private final Long memberId;

	public UserPrincipal(Member member) {
		super(member.getName(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
		this.memberId = member.getId();
	}

	public Long getMemberId() {
		return memberId;
	}
}

