package com.chzzkzzal.zzal.business.domain.zzal.metadata;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Gif implements MediaMeta {
	private ZzalType zzalType;
	private long size;
	private int width;
	private int height;
	private int frameCount;
	private double totalDuration;
	private String contentType;
	private String fileName;

	public Gif(long size, int width, int height, int frameCount, double totalDuration, String contentType,
		String fileName) {
		this.zzalType = ZzalType.GIF;
		this.size = size;
		this.width = width;
		this.height = height;
		this.frameCount = frameCount;
		this.totalDuration = totalDuration;
		this.contentType = contentType;
		this.fileName = fileName;
	}

}
