package com.chzzkzzal.member.business.application.query;

import com.chzzkzzal.common.annotation.PublicApi;

@PublicApi
public record MemberInfo(
	Long id,
	String channelId,
	String channelName
) {
}
