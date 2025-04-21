package com.chzzkzzal.zzal.domain.model.metadata;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

public enum MultipartFileContentType {
	GIF("image/gif"),
	JPEG("image/jpeg"), JPG("image/jpg"), PNG("image/png"), SVG("image/svg+xml"),
	;

	private final String type;

	MultipartFileContentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static MultipartFileContentType fromString(String contentType) {
		for (MultipartFileContentType ict : values()) {
			if (ict.getType().equalsIgnoreCase(contentType)) {
				return ict;
			}
		}
		throw new MetadataUnsupportedFormatException();
	}

	public ZzalType toZzalType() {
		switch (this) {
			case GIF:
				return ZzalType.GIF;
			case JPEG:
			case JPG:
			case PNG:
			case SVG:
				return ZzalType.PIC;
			default:
				throw new MetadataUnsupportedFormatException();
		}
	}
}
