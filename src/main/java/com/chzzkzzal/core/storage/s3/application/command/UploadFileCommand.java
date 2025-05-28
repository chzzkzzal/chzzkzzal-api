package com.chzzkzzal.core.storage.s3.application.command;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public record UploadFileCommand(
	InputStream inputStream,
	byte[] bytes,
	String originalName,
	String contentType
) {
	public static UploadFileCommand from(MultipartFile file) {
		try {
			return new UploadFileCommand(
				file.getInputStream(),
				file.getBytes(),
				file.getOriginalFilename(),
				file.getContentType()
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
