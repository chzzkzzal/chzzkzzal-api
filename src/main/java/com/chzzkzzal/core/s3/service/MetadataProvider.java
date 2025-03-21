package com.chzzkzzal.core.s3.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MetadataProvider {

	public void getMetadata(MultipartFile multipartFile) {
		try {
			// 파일 전체를 바이트 배열로 읽어들임
			byte[] fileBytes = multipartFile.getBytes();

			// 이미지 크기 및 형식, 파일 이름 출력 (이미지 읽기)
			try (ByteArrayInputStream bais1 = new ByteArrayInputStream(fileBytes)) {
				BufferedImage image = ImageIO.read(bais1);
				if (image == null) {
					throw new IllegalArgumentException("이미지 파일이 아닙니다.");
				}
				int width = image.getWidth();
				int height = image.getHeight();
				System.out.println("사이즈(byte): "+multipartFile.getSize());
				System.out.println("이미지 크기: " + width + "x" + height);
				System.out.println("이미지 형식: " + multipartFile.getContentType());
				System.out.println("파일 이름: " + multipartFile.getOriginalFilename());
			}

			// GIF 파일의 프레임 정보 및 전체 지속 시간 계산
			if ("image/gif".equals(multipartFile.getContentType())) {
				try (ByteArrayInputStream bais2 = new ByteArrayInputStream(fileBytes);
					 ImageInputStream imageInputStream = ImageIO.createImageInputStream(bais2)) {

					Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
					if (readers.hasNext()) {
						ImageReader reader = readers.next();
						reader.setInput(imageInputStream);

						int numFrames = reader.getNumImages(true);
						System.out.println("프레임 수: " + numFrames);

						int totalDelay = 0; // delayTime의 누적합 (1/100초 단위)
						for (int i = 0; i < numFrames; i++) {
							IIOMetadata metadata = reader.getImageMetadata(i);
							IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree("javax_imageio_gif_image_1.0");
							IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);

							if (gce != null) {
								String delayTime = gce.getAttribute("delayTime");
								int delay = Integer.parseInt(delayTime);
								totalDelay += delay;
								// System.out.println("프레임 " + i + " 지속 시간: " + (delay * 10) + " ms");
							}
						}
						double totalSeconds = (totalDelay * 10) / 1000.0;
						System.out.println("전체 지속 시간: " + totalSeconds + " 초");
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("메타데이터 추출 중 오류 발생: " + e.getMessage(), e);
		}
	}
}
