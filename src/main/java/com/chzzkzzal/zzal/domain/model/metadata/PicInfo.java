package com.chzzkzzal.zzal.domain.model.metadata;

public class PicInfo {
    private long size;
    private int width;
    private int height;
    private String contentType;
    private String fileName;

    public PicInfo(long size, int width, int height, String contentType, String fileName) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    // getters and setters
    public long getSize() { return size; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getContentType() { return contentType; }
    public String getFileName() { return fileName; }
}
