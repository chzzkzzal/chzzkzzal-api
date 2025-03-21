package com.chzzkzzal.zzal.domain.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetadataProvider<T> {

	private final FileValidator fileValidator;
	private final ImageMetadataExtractor imageMetadataExtractor;
	private final GifMetadataExtractor gifMetadataExtractor;

	public Object getMetadata(MultipartFile multipartFile) {
		try {
			fileValidator.validateExtension(multipartFile.getOriginalFilename());

			String contentType = multipartFile.getContentType();
			if (contentType == null) {
				throw new IllegalArgumentException("파일 형식 정보가 없습니다.");
			}

			ImageContentType imageContentType = ImageContentType.fromString(contentType);

			switch (imageContentType) {
				case GIF:
					GifInfo gifInfo = gifMetadataExtractor.extract(multipartFile);
					return gifInfo;
				case JPEG:
				case JPG:
				case PNG:
				case SVG:
					ImageInfo imageInfo = imageMetadataExtractor.extract(multipartFile);
					return imageInfo;

					default:
					throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다: " + contentType);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("메타데이터 추출 중 오류 발생: " + e.getMessage(), e);
		}
	}
}
