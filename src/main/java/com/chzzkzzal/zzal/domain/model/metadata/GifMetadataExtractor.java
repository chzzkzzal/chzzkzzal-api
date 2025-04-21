package com.chzzkzzal.zzal.domain.model.metadata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
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
		// contentType null 체크는 MetadataProvider에서 수행
		byte[] fileBytes = readFileBytes(file);

		int numFrames = countFrames(fileBytes);
		double totalSeconds = calculateTotalSeconds(fileBytes, numFrames);
		BufferedImage image = readImage(fileBytes);

		return new GifInfo(
			file.getSize(),
			image.getWidth(),
			image.getHeight(),
			numFrames,
			totalSeconds,
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

	private int countFrames(byte[] fileBytes) {
		try (ImageInputStream iis = createImageInputStream(fileBytes)) {
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
			if (!readers.hasNext()) {
				throw new MetadataUnsupportedFormatException();
			}
			ImageReader reader = readers.next();
			reader.setInput(iis);
			return reader.getNumImages(true);
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private double calculateTotalSeconds(byte[] fileBytes, int numFrames) {
		try (ImageInputStream iis = createImageInputStream(fileBytes)) {
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
			if (!readers.hasNext()) {
				throw new MetadataUnsupportedFormatException();
			}
			ImageReader reader = readers.next();
			reader.setInput(iis);

			int totalDelay = 0;
			for (int i = 0; i < numFrames; i++) {
				IIOMetadataNode root = (IIOMetadataNode)reader.getImageMetadata(i)
					.getAsTree("javax_imageio_gif_image_1.0");
				IIOMetadataNode gce = (IIOMetadataNode)root
					.getElementsByTagName("GraphicControlExtension").item(0);
				if (gce != null) {
					try {
						totalDelay += Integer.parseInt(gce.getAttribute("delayTime"));
					} catch (NumberFormatException e) {
						throw new MetadataExtractionFailedException(e);
					}
				}
			}
			return totalDelay * 10 / 1000.0;
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private BufferedImage readImage(byte[] fileBytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes)) {
			BufferedImage image = ImageIO.read(bais);
			if (image == null) {
				throw new MetadataUnsupportedFormatException();
			}
			return image;
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private ImageInputStream createImageInputStream(byte[] bytes) {
		try {
			return ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}
}
