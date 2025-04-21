package com.chzzkzzal.zzal.domain.metadata.extractor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.metadata.image.GifDurationCalculator;
import com.chzzkzzal.zzal.domain.metadata.image.GifFrameAnalyzer;
import com.chzzkzzal.zzal.domain.metadata.image.ImageReader;
import com.chzzkzzal.zzal.domain.metadata.vo.GifInfo;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GifMetadataExtractor extends AbstractMetadataExtractor<GifInfo> {

	private final GifFrameAnalyzer frameAnalyzer;
	private final GifDurationCalculator durationCalculator;

	@Override
	public boolean supports(ZzalType type) {
		return type == ZzalType.GIF;
	}

	@Override
	protected GifInfo parseMetadata(byte[] bytes, MultipartFile file) {
		try {
			ImageReader reader = new ImageReader(bytes);

			int frames = frameAnalyzer.countFrames(reader);
			double seconds = durationCalculator.calculateTotalDuration(reader, frames);
			BufferedImage firstFrame = readFirstFrame(bytes);

			return new GifInfo(
				file.getSize(),
				firstFrame.getWidth(),
				firstFrame.getHeight(),
				frames,
				seconds,
				file.getContentType(),
				file.getOriginalFilename()
			);
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private BufferedImage readFirstFrame(byte[] bytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			BufferedImage img = ImageIO.read(bais);
			if (img == null) {
				throw new MetadataUnsupportedFormatException();
			}
			return img;
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}
