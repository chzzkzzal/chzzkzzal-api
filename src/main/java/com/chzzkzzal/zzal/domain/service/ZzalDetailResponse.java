package com.chzzkzzal.zzal.domain.service;

import java.time.LocalDateTime;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.entity.Zzal;
import com.chzzkzzal.zzal.domain.model.entity.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.metadata.PicInfo;

public record ZzalDetailResponse(
	Long zzalId,
	String url,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	Long writerId,
	String writerChannelName,
	ZzalMetaInfo zzalMetaInfo

	) {

	public static ZzalDetailResponse toResponse(Zzal zzal, Member member){
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
