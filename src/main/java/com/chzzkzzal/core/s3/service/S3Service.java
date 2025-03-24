package com.chzzkzzal.core.s3.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	List<String> uploadFiles(List<MultipartFile> multipartFiles);
	String uploadFile(MultipartFile multipartFile);

	void deleteFile(String fileName);
	String getFileUrl(String fileName);

}
