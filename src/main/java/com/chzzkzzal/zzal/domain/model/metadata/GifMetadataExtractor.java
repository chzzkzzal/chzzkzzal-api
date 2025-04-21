package com.chzzkzzal.zzal.domain.model.metadata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.exception.metadata.MetadataExtractionFailedException;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class GifMetadataExtractor implements MetadataExtractor {

	@Override
	public GifInfo extract(MultipartFile file) {
		byte[] fileBytes = getFileBytes(file);

		try (ByteArrayInputStream metadataStream = new ByteArrayInputStream(fileBytes);
			 ImageInputStream imageInputStream = ImageIO.createImageInputStream(metadataStream)) {

			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
			if (!readers.hasNext()) {
				throw new MetadataUnsupportedFormatException();
			}
			ImageReader reader = readers.next();
			reader.setInput(imageInputStream);

			int numFrames = reader.getNumImages(true);
			int totalDelay = 0;
			for (int i = 0; i < numFrames; i++) {
				IIOMetadata metadata = reader.getImageMetadata(i);
				IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree("javax_imageio_gif_image_1.0");
				IIOMetadataNode gce = (IIOMetadataNode)root.getElementsByTagName("GraphicControlExtension").item(0);
				if (gce != null) {
					String delayTime = gce.getAttribute("delayTime");
					try {
						totalDelay += Integer.parseInt(delayTime);
					} catch (NumberFormatException e) {
						// 지연시간 파싱 실패
						throw new MetadataExtractionFailedException(e);
					}
				}
			}
			double totalSeconds = (totalDelay * 10) / 1000.0;

			// 새로운 스트림을 사용하여 이미지 크기를 추출 (스트림 재사용 문제 해결)
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileBytes));
			if (image == null) {
				throw new MetadataUnsupportedFormatException();
			}
			int width = image.getWidth();
			int height = image.getHeight();
			return new GifInfo(file.getSize(), width, height, numFrames, totalSeconds, file.getContentType(),
				file.getOriginalFilename());

		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private byte[] getFileBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}
