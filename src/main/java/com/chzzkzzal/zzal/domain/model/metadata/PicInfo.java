package com.chzzkzzal.zzal.domain.model.metadata;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PicInfo implements ZzalMetaInfo {
	private ZzalType zzalType;
	private long size;
	private int width;
	private int height;
	private String contentType;
	private String fileName;

	public PicInfo(long size, int width, int height, String contentType, String fileName) {
		this.zzalType = ZzalType.PIC;
		this.size = size;
		this.width = width;
		this.height = height;
		this.contentType = contentType;
		this.fileName = fileName;
	}

}
