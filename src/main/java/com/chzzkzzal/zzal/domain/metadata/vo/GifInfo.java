package com.chzzkzzal.zzal.domain.metadata.vo;

import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GifInfo implements ZzalMetaInfo {
	private ZzalType zzalType;
	private long size;
	private int width;
	private int height;
	private int frameCount;
	private double totalDuration;
	private String contentType;
	private String fileName;

	public GifInfo(long size, int width, int height, int frameCount, double totalDuration, String contentType,
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
