package com.chzzkzzal.zzal.domain.model.metadata;

public enum AllowedFileExtension {
    JPG("jpg"),
    JPEG("jpeg"),
    SVG("svg"),
    PNG("png"),
    GIF("gif");

    private final String extension;

    AllowedFileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static boolean isAllowed(String ext) {
        for (AllowedFileExtension allowed : values()) {
            if (allowed.getExtension().equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
}
