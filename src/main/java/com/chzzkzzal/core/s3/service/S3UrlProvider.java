package com.chzzkzzal.core.s3.service;

import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.chzzkzzal.core.s3.S3Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class S3UrlProvider {

	private final S3Repository s3Repository;

	public String getUrl(String fileName) {
		try {
			URL url = s3Repository.getFileUrl(fileName);
			return url.toString();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 URL 조회에 실패했습니다.");
		}
	}
}
