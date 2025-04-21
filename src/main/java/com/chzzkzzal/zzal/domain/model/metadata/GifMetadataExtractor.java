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

import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataExtractionFailedException;
import com.chzzkzzal.zzal.exception.metadata.MetadataIOException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

@Component
public class GifMetadataExtractor
	extends AbstractMetadataExtractor<GifInfo> {

	@Override
	public boolean supports(ZzalType type) {
		return type == ZzalType.GIF;
	}

	@Override
	protected GifInfo parseMetadata(byte[] bytes, MultipartFile file) {
		int frames = countFrames(bytes);
		double seconds = calculateTotalSeconds(bytes, frames);
		BufferedImage img = readCommonImage(bytes);
		return new GifInfo(
			file.getSize(), img.getWidth(), img.getHeight(),
			frames, seconds,
			file.getContentType(), file.getOriginalFilename()
		);
	}

	private ImageInputStream createStream(byte[] bytes) {
		try {
			return ImageIO.createImageInputStream(
				new ByteArrayInputStream(bytes)
			);
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private int countFrames(byte[] bytes) {
		try (ImageInputStream iis = createStream(bytes)) {
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("gif");
			if (!it.hasNext())
				throw new MetadataUnsupportedFormatException();
			ImageReader reader = it.next();
			reader.setInput(iis);
			return reader.getNumImages(true);
		} catch (IOException e) {
			throw new MetadataIOException(e);
		}
	}

	private double calculateTotalSeconds(byte[] bytes, int numFrames) {
		try (ImageInputStream iis = createStream(bytes)) {
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("gif");
			if (!it.hasNext())
				throw new MetadataUnsupportedFormatException();
			ImageReader reader = it.next();
			reader.setInput(iis);
			int totalDelay = 0;
			for (int i = 0; i < numFrames; i++) {
				IIOMetadataNode root = (IIOMetadataNode)
					reader.getImageMetadata(i)
						.getAsTree("javax_imageio_gif_image_1.0");
				IIOMetadataNode gce = (IIOMetadataNode)
					root.getElementsByTagName("GraphicControlExtension")
						.item(0);
				if (gce != null) {
					try {
						totalDelay += Integer.parseInt(
							gce.getAttribute("delayTime")
						);
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

	private BufferedImage readCommonImage(byte[] bytes) {
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
