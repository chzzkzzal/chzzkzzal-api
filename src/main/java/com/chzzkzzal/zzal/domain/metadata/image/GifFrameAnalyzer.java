package com.chzzkzzal.zzal.domain.metadata.image;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class GifFrameAnalyzer {
	public int countFrames(ImageReader reader) throws IOException {
		return reader.getNumImages();
	}
}
