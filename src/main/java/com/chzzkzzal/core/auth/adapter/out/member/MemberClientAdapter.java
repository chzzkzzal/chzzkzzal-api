package com.chzzkzzal.core.auth.adapter.out.member;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.application.port.out.LoadMemberQueryPort;
import com.chzzkzzal.core.auth.application.port.out.SaveMemberCommandPort;
import com.chzzkzzal.member.business.application.port.out.LoadMemberQuery;
import com.chzzkzzal.member.business.application.port.out.SaveMemberCommand;
import com.chzzkzzal.member.business.application.query.MemberInfo;

import lombok.RequiredArgsConstructor;

@Component("AuthMemberClientAdapter")
@RequiredArgsConstructor
public class MemberClientAdapter implements LoadMemberQueryPort, SaveMemberCommandPort {
	private final LoadMemberQuery memberQuery;
	private final SaveMemberCommand saveMemberCommand;

	@Override
	public MemberInfo loadById(Long memberId) {
		return memberQuery.loadById(memberId);
	}

	@Override
	public MemberInfo loadByChannelId(String channelId) {
		return memberQuery.loadByChannelId(channelId);
	}

	@Override
	public MemberInfo saveIfNotExist(String externalId, String channelName) {
		return saveMemberCommand.saveIfNotExist(externalId, channelName);
	}
}

