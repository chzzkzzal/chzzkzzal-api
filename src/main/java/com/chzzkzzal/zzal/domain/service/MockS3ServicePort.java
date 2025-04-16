package com.chzzkzzal.zzal.domain.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.s3.service.S3ServicePort;

// @Primary
@Service
public class MockS3ServicePort implements S3ServicePort {

	@Value("${file.upload-dir}") // application.properties에서 설정
	private String uploadDir;

	@Override
	public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
		return multipartFiles.stream()
			.map(this::uploadFile)
			.toList();
	}

	@Override
	public String uploadFile(MultipartFile multipartFile) {
		try {
			// 디렉토리 생성
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// 고유한 파일명 생성
			String originalFilename = multipartFile.getOriginalFilename();
			String uniqueFileName = UUID.randomUUID() +
				(originalFilename != null ?
					originalFilename.substring(originalFilename.lastIndexOf(".")) :
					"");

			// 파일 저장 경로
			Path filePath = Paths.get(uploadDir, uniqueFileName);

			// 파일 저장
			multipartFile.transferTo(filePath.toFile());

			// 웹 접근 가능한 상대 경로 반환
			return "/zzal/" + uniqueFileName;
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패", e);
		}
	}

	@Override
	public void deleteFile(String fileName) {
		try {
			// 파일명에서 경로 제거 (만약 "/zzal/"이 포함되어 있다면)
			String simpleFileName = fileName.replace("/zzal/", "");
			Path filePath = Paths.get(uploadDir, simpleFileName);
			java.nio.file.Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new RuntimeException("파일 삭제 실패", e);
		}
	}

	@Override
	public String getFileUrl(String fileName) {
		return "http://localhost:8080" + (fileName.startsWith("/zzal/") ? fileName : "/zzal/" + fileName);
	}
}
