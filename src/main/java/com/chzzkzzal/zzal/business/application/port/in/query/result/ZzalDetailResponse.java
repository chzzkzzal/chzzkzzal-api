package com.chzzkzzal.zzal.business.application.port.in.query.result;

import java.time.LocalDateTime;

import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public record ZzalDetailResponse(
	Long zzalId,
	String url,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	Long writerId,
	String writerChannelName,
	MediaMeta mediaMeta

) {

	public static ZzalDetailResponse toResponse(Zzal zzal, MemberInfo member) {
		return new ZzalDetailResponse(
			zzal.getId(),
			zzal.getUrl(),
			zzal.getTitle(),
			zzal.getCreatedAt(),
			zzal.getUpdatedAt(),
			member.id(),
			member.channelName(),
			zzal.getMetaInfo()
		);
	}

	public static ZzalDetailResponse toResponse(Zzal zzal, Member member) {
		return new ZzalDetailResponse(
			zzal.getId(),
			zzal.getUrl(),
			zzal.getTitle(),
			zzal.getCreatedAt(),
			zzal.getUpdatedAt(),
			member.getId(),
			member.getChannelName(),
			zzal.getMetaInfo()
		);
	}
}
