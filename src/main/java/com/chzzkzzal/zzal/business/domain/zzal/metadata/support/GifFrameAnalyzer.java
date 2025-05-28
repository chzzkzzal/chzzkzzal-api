package com.chzzkzzal.zzal.business.domain.zzal.metadata.support;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class GifFrameAnalyzer {
	public int countFrames(CustomImageReader reader) throws IOException {
		return reader.getNumImages();
	}
}
