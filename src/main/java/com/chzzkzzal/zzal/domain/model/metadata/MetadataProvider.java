package com.chzzkzzal.zzal.domain.model.metadata;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataContentTypeNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataExtractionFailedException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetadataProvider {

	private final FileValidator fileValidator;
	private final GifMetadataExtractor gifMetadataExtractor;
	private final PicMetadataExtractor picMetadataExtractor;

	public ZzalMetaInfo getMetadata(MultipartFile multipartFile) {
		try {
			fileValidator.validateExtension(multipartFile.getOriginalFilename());

			String contentType = multipartFile.getContentType();
			if (contentType == null) {
				throw new MetadataContentTypeNullException();
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
					throw new MetadataUnsupportedFormatException();
			}
		} catch (Exception e) {
			throw new MetadataExtractionFailedException(e);
		}
	}
}
