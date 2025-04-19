package com.chzzkzzal.core.storage.s3.application;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3ServicePort {
	List<String> uploadFiles(List<MultipartFile> multipartFiles);

	String uploadFile(MultipartFile multipartFile);

	void deleteFile(String fileName);

	String getFileUrl(String fileName);

}
