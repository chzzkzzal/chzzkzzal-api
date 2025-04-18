package com.chzzkzzal.core.storage.s3.infratructure.internal;

import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.chzzkzzal.core.storage.s3.domain.S3Repository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3UrlProvider {

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
