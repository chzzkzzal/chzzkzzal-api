package com.chzzkzzal.zzal.domain.model.metadata;

import java.util.HashMap;
import java.util.Map;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MultipartFileContentType {
	GIF("image/gif"),
	JPEG("image/jpeg"), JPG("image/jpg"), PNG("image/png"), SVG("image/svg+xml"),
	;

	private final String type;

	private static final Map<String, MultipartFileContentType> TYPE_MAP = new HashMap<>();

	static {
		for (MultipartFileContentType type : MultipartFileContentType.values()) {
			TYPE_MAP.put(type.getType().toLowerCase(), type);
		}
	}

	public static MultipartFileContentType fromString(String contentType) {
		MultipartFileContentType result = TYPE_MAP.get(contentType.toLowerCase());
		if (result == null) {
			throw new MetadataUnsupportedFormatException();
		}
		return result;
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
