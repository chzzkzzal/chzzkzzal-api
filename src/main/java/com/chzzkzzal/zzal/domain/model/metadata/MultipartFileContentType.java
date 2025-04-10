package com.chzzkzzal.zzal.domain.model.metadata;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;

public enum MultipartFileContentType {
    GIF("image/gif"),
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png"),
    SVG("image/svg+xml");

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
        throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다: " + contentType);
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
                throw new IllegalArgumentException("지원되지 않는 MultipartFileContentType: " + this);
        }
    }
}
