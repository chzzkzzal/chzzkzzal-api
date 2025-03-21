package com.chzzkzzal.zzal.domain.model.metadata;

import lombok.Getter;

@Getter
public class GifInfo {
    private final long size;
    private final int width;
    private final int height;
    private final int frameCount;
    private final double totalDuration;
    private final String contentType;
    private final String fileName;

    public GifInfo(long size, int width, int height, int frameCount, double totalDuration, String contentType,
        String fileName) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
        this.totalDuration = totalDuration;
        this.contentType = contentType;
        this.fileName = fileName;
    }


}
