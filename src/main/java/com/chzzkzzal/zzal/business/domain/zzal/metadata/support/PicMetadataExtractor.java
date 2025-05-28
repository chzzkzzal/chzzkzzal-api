package com.chzzkzzal.zzal.business.domain.zzal.metadata.support;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.domain.zzal.metadata.Pic;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class PicMetadataExtractor extends AbstractMetadataExtractor<Pic> {

	@Override
	public boolean supports(ZzalType type) {
		return type == ZzalType.PIC;
	}

	@Override
	protected Pic parseMetadata(byte[] bytes, final String originalName, final String contentType) {
		BufferedImage img = readImage(bytes);
		return new Pic(
			bytes.length, img.getWidth(), img.getHeight(),
			contentType, originalName
		);
	}

	private BufferedImage readImage(byte[] bytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			BufferedImage img = ImageIO.read(bais);
			if (img == null)
				throw new MetadataUnsupportedFormatException();
			return img;
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}

