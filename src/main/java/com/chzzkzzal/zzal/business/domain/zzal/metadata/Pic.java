package com.chzzkzzal.zzal.business.domain.zzal.metadata;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Pic implements MediaMeta {
	private ZzalType zzalType;
	private long size;
	private int width;
	private int height;
	private String contentType;
	private String fileName;

	public Pic(long size, int width, int height, String contentType, String fileName) {
		this.zzalType = ZzalType.PIC;
		this.size = size;
		this.width = width;
		this.height = height;
		this.contentType = contentType;
		this.fileName = fileName;
	}

}
