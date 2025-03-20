package com.chzzkzzal.core.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chzzkzzal.member.domain.Member;

public class MemberUserDetails implements UserDetails {

	private final Member member;

	public MemberUserDetails(Member member) {
		this.member = member;
	}

	// 권한 목록
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 예: 모두 ROLE_USER
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		// OAuth 가입만 한다면, 비밀번호가 없을 수 있으므로 null or ""
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// 인증 식별자 (예: channelId or DB ID)
		return member.getChannelId();
	}

	// 나머지 메서드들
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Member getMember() {
		return this.member;
	}
}
