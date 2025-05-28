package com.chzzkzzal.zzal.business.domain.zzal.metadata.support;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

public class CustomImageReader implements AutoCloseable {
	private static final String GIF_FORMAT_NAME = "gif";

	private final javax.imageio.ImageReader reader;
	private final javax.imageio.stream.ImageInputStream stream;

	public CustomImageReader(byte[] imageBytes) throws IOException {
		this.stream = createImageInputStream(imageBytes);
		this.reader = getGifReader();
		this.reader.setInput(stream);
	}

	private javax.imageio.stream.ImageInputStream createImageInputStream(byte[] bytes) throws IOException {
		return ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
	}

	private javax.imageio.ImageReader getGifReader() {
		java.util.Iterator<javax.imageio.ImageReader> readers =
			ImageIO.getImageReadersByFormatName(GIF_FORMAT_NAME);
		if (!readers.hasNext()) {
			throw new MetadataUnsupportedFormatException();
		}
		return readers.next();
	}

	public int getNumImages() throws IOException {
		return reader.getNumImages(true);
	}

	public javax.imageio.metadata.IIOMetadata getImageMetadata(int imageIndex) throws IOException {
		return reader.getImageMetadata(imageIndex);
	}

	public Dimension getDimension(byte[] imageBytes) throws IOException {
		try (InputStream is = toFirstFrameInputStream(imageBytes)) {
			BufferedImage img = ImageIO.read(is);
			if (img == null)
				throw new MetadataUnsupportedFormatException();
			return new Dimension(img.getWidth(), img.getHeight());
		}
	}

	private InputStream toFirstFrameInputStream(byte[] imageBytes) throws IOException {
		// stream.reset() 지원 여부 등을 감안해 새 ByteArrayInputStream 사용
		return new ByteArrayInputStream(imageBytes);
	}

	@Override
	public void close() throws IOException {
		reader.dispose();
		stream.close();
	}

	public record Dimension(int width, int height) {
	}

}
