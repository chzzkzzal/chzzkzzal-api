package com.chzzkzzal.zzal.domain.metadata.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

public class ImageReader implements AutoCloseable {
	private static final String GIF_FORMAT_NAME = "gif";

	private final javax.imageio.ImageReader reader;
	private final javax.imageio.stream.ImageInputStream stream;

	public ImageReader(byte[] imageBytes) throws IOException {
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

	@Override
	public void close() throws IOException {
		reader.dispose();
		stream.close();
	}
}
