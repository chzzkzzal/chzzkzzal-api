package com.chzzkzzal.zzal.business.application.port.in.command;

import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;

public record SaveZzalCommand(
	String channelId,
	Member member,
	MediaMeta mediaMeta,
	String title,
	String fileUrl
) {
}
