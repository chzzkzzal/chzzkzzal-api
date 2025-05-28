package com.chzzkzzal.zzal_tag.web;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ZzalSummaryResponse {
	private Long id;
	private String title;
	private String url;

	public static ZzalSummaryResponse from(Zzal z) {
		return new ZzalSummaryResponse(z.getId(), z.getTitle(), z.getUrl());
	}
}
