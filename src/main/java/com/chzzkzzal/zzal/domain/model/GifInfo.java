package com.chzzkzzal.zzal.domain.model;

import lombok.Getter;

@Getter
public class GifInfo {
    private final long size;
    private final int width;
    private final int height;
    private final int frameCount;
    private final double totalDuration;

    public GifInfo(long size, int width, int height, int frameCount, double totalDuration) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
        this.totalDuration = totalDuration;
    }

    // getters and setters
    public int getFrameCount() {
        return frameCount;
    }
    public double getTotalDuration() {
        return totalDuration;
    }
}
