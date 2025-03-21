package com.chzzkzzal.zzal.domain.model;

public enum ImageContentType {
    GIF("image/gif"),
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png"),
    SVG("image/svg+xml");

    private final String type;

    ImageContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ImageContentType fromString(String contentType) {
        for (ImageContentType ict : values()) {
            if (ict.getType().equalsIgnoreCase(contentType)) {
                return ict;
            }
        }
        throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다: " + contentType);
    }
}
