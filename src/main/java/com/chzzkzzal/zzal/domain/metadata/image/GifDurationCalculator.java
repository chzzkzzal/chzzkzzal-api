package com.chzzkzzal.zzal.domain.metadata.image;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.exception.metadata.MetadataExtractionFailedException;

@Component
public class GifDurationCalculator {
	private static final String GIF_METADATA_FORMAT = "javax_imageio_gif_image_1.0";
	private static final String GCE_TAG_NAME = "GraphicControlExtension";
	private static final String DELAY_TIME_ATTRIBUTE = "delayTime";
	private static final double DELAY_TIME_FACTOR = 10.0 / 1000.0;

	public double calculateTotalDuration(ImageReader reader, int numFrames) throws IOException {
		int totalDelay = 0;

		for (int i = 0; i < numFrames; i++) {
			int frameDelay = extractFrameDelay(reader, i);
			totalDelay += frameDelay;
		}

		return totalDelay * DELAY_TIME_FACTOR;
	}

	private int extractFrameDelay(ImageReader reader, int frameIndex) throws IOException {
		javax.imageio.metadata.IIOMetadataNode root = (javax.imageio.metadata.IIOMetadataNode)
			reader.getImageMetadata(frameIndex).getAsTree(GIF_METADATA_FORMAT);

		javax.imageio.metadata.IIOMetadataNode gce = (javax.imageio.metadata.IIOMetadataNode)
			root.getElementsByTagName(GCE_TAG_NAME).item(0);

		if (gce == null) {
			return 0;
		}

		try {
			return Integer.parseInt(gce.getAttribute(DELAY_TIME_ATTRIBUTE));
		} catch (NumberFormatException e) {
			throw new MetadataExtractionFailedException(e);
		}
	}
}
