package com.chzzkzzal.zzal.domain.metadata.validator;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AllowedFileExtension {
	GIF("gif"),

	JPG("jpg"), JPEG("jpeg"), SVG("svg"), PNG("png"),
	;

	private final String extension;

	private static final Map<String, AllowedFileExtension> EXTENSION_MAP = new HashMap<>();

	static {
		for (AllowedFileExtension allowed : values()) {
			EXTENSION_MAP.put(allowed.getExtension().toLowerCase(), allowed);
		}
	}

	public static boolean isAllowed(String ext) {
		if (ext == null) {
			return false;
		}
		return EXTENSION_MAP.containsKey(ext.toLowerCase());
	}
}
