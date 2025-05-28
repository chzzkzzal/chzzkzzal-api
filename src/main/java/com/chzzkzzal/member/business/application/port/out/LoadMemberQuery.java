package com.chzzkzzal.member.business.application.port.out;

import com.chzzkzzal.common.annotation.PublicApi;
import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.member.business.domain.Member;

@PublicApi("External use by zzal, auth, etc.")
public interface LoadMemberQuery {
	MemberInfo loadById(Long memberId);

	MemberInfo loadByChannelId(String channelId);

	Member loadEntityById(Long memberId);
}
