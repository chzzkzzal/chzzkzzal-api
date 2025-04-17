package com.chzzkzzal.core.s3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServicePortImpl implements S3ServicePort {

	private final S3Uploader s3Uploader;
	private final S3Deleter s3Deleter;
	private final S3UrlProvider s3UrlProvider;

	public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
		List<String> fileNameList = new ArrayList<>();
		multipartFiles.forEach(file -> {
			String fileName = s3Uploader.upload(file);
			fileNameList.add(fileName);
		});
		return fileNameList;
	}

	public String uploadFile(MultipartFile multipartFiles) {

		String fileName = s3Uploader.upload(multipartFiles);

		return fileName;
	}

	public void deleteFile(String fileName) {
		s3Deleter.delete(fileName);
	}

	public String getFileUrl(String fileName) {
		return s3UrlProvider.getUrl(fileName);
	}

}
