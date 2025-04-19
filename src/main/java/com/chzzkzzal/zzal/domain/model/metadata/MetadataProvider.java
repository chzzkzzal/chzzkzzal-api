package com.chzzkzzal.zzal.domain.model.metadata;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetadataProvider<T> {

	private final FileValidator fileValidator;
	private final GifMetadataExtractor gifMetadataExtractor;
	private final PicMetadataExtractor picMetadataExtractor;

	public ZzalMetaInfo getMetadata(MultipartFile multipartFile) {
		try {
			fileValidator.validateExtension(multipartFile.getOriginalFilename());

			String contentType = multipartFile.getContentType();
			if (contentType == null) {
				throw new IllegalArgumentException("파일 형식 정보가 없습니다.");
			}

			MultipartFileContentType zzalContentType = MultipartFileContentType.fromString(contentType);
			ZzalType zzalType = zzalContentType.toZzalType();

			switch (zzalType) {
				case GIF:
					GifInfo gifInfo = gifMetadataExtractor.extract(multipartFile);
					return gifInfo;
				case PIC:
					PicInfo picInfo = picMetadataExtractor.extract(multipartFile);
					return picInfo;

					default:
					throw new IllegalArgumentException("지원되지 않는 이미지 형식입니다: " + contentType);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("메타데이터 추출 중 오류 발생: " + e.getMessage(), e);
		}
	}
}
