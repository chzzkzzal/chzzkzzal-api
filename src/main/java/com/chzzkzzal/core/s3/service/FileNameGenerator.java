package com.chzzkzzal.core.s3.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FileNameGenerator {

	public String generate(String originalFileName) {
		String fileExtension = getFileExtension(originalFileName);
		String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String uuid = UUID.randomUUID().toString();

		// 최종적으로 S3 키(= path)가 "yyyy/MM/dd/uuid.ext" 형태가 됩니다.
		return datePrefix + "/" + uuid + fileExtension;
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"잘못된 형식의 파일(" + fileName + ") 입니다.");
		}
	}
}
