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

	public PicInfo extract(MultipartFile file) {
		byte[] fileBytes = new byte[0];
		try {
			fileBytes = file.getBytes();
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
		try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes)) {
			BufferedImage image = ImageIO.read(bais);
			if (image == null) {
				throw new MetadataUnsupportedFormatException();
			}
			int width = image.getWidth();
			int height = image.getHeight();

			return new PicInfo(file.getSize(), width, height, file.getContentType(), file.getOriginalFilename());
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}
