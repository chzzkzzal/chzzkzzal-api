package com.chzzkzzal.zzal.domain.metadata.extractor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.metadata.vo.PicInfo;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class PicMetadataExtractor
	extends AbstractMetadataExtractor<PicInfo> {

	@Override
	public boolean supports(ZzalType type) {
		return type == ZzalType.PIC;
	}

	@Override
	protected PicInfo parseMetadata(byte[] bytes, MultipartFile file) {
		BufferedImage img = readImage(bytes);
		return new PicInfo(
			file.getSize(), img.getWidth(), img.getHeight(),
			file.getContentType(), file.getOriginalFilename()
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

