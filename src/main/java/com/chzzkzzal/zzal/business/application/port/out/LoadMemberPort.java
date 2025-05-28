package com.chzzkzzal.zzal.business.application.port.out;

import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.member.business.domain.Member;

public interface LoadMemberPort {
	MemberInfo loadMember(Long memberId);

	Member loadMemberEntity(Long memberId);
}
