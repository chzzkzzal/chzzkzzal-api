package com.chzzkzzal.zzal.infrastructure.member;

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.business.application.port.out.LoadMemberQuery;
import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.application.port.out.LoadMemberPort;

import lombok.RequiredArgsConstructor;

@Component("ZzalMemberClientAdapter")
@RequiredArgsConstructor
public class MemberClientAdapter implements LoadMemberPort {
	private final LoadMemberQuery memberQuery;

	@Override
	public MemberInfo loadMember(Long memberId) {
		return memberQuery.loadById(memberId);
	}

	@Override
	public Member loadMemberEntity(final Long memberId) {
		return memberQuery.loadEntityById(memberId);
	}
}
