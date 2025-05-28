package com.chzzkzzal.zzal.business.domain.zzal.metadata.support;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.domain.zzal.metadata.Gif;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GifMetadataExtractor extends AbstractMetadataExtractor<Gif> {

	private final GifFrameAnalyzer frameAnalyzer;
	private final GifDurationCalculator durationCalculator;

	@Override
	public boolean supports(ZzalType type) {
		return type == ZzalType.GIF;
	}

	@Override
	public Gif parseMetadata(final byte[] bytes, final String originalName, final String contentType) {
		try {
			CustomImageReader reader = new CustomImageReader(bytes);
			int frames = frameAnalyzer.countFrames(reader);
			double seconds = durationCalculator.calculateTotalDuration(reader, frames);
			CustomImageReader.Dimension dimension = reader.getDimension(bytes);

			return new Gif(
				bytes.length,
				dimension.width(),
				dimension.height(),
				frames,
				seconds,
				contentType,
				originalName
			);
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

}
