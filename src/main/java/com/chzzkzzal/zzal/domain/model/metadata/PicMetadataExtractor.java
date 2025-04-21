package com.chzzkzzal.zzal.domain.model.metadata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class PicMetadataExtractor implements MetadataExtractor {

	@Override
	public PicInfo extract(MultipartFile file) {
		byte[] fileBytes = readFileBytes(file);
		BufferedImage image = readImage(fileBytes);

		return new PicInfo(
			file.getSize(),
			image.getWidth(),
			image.getHeight(),
			file.getContentType(),
			file.getOriginalFilename()
		);
	}

	private byte[] readFileBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private BufferedImage readImage(byte[] bytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			BufferedImage image = ImageIO.read(bais);
			if (image == null) {
				throw new MetadataUnsupportedFormatException();
			}
			return image;
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}
