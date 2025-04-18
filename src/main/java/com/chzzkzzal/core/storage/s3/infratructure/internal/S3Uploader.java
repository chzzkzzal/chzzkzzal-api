package com.chzzkzzal.core.storage.s3.infratructure.internal;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.chzzkzzal.core.storage.s3.domain.FileNameGenerator;
import com.chzzkzzal.core.storage.s3.infratructure.repository.S3Repository;
import com.chzzkzzal.zzal.domain.model.metadata.FileValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {

	private final S3Repository s3Repository;
	private final FileNameGenerator fileNameGenerator;
	private final FileValidator fileValidator;

	public String upload(MultipartFile file) {
		// 확장자 검증
		fileValidator.validateExtension(file.getOriginalFilename());

		// 파일명 생성
		String fileName = fileNameGenerator.generate(file.getOriginalFilename());

		// 메타 데이터 세팅
		ObjectMetadata metadata = setMetaData(file);

		// S3에 업로드
		return uploadToS3(file, fileName, metadata);
	}

	private String uploadToS3(MultipartFile file, String fileName, ObjectMetadata metadata) {
		try (InputStream inputStream = file.getInputStream()) {
			s3Repository.putObject(fileName, inputStream, metadata);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
		}
		return fileName;
	}

	private ObjectMetadata setMetaData(MultipartFile file) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());
		return metadata;
	}
}
