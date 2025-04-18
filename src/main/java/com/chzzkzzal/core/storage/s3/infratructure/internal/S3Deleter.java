package com.chzzkzzal.core.storage.s3.infratructure.internal;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.storage.s3.domain.S3Repository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Deleter {

	private final S3Repository s3Repository;

	public void delete(String fileName) {
		s3Repository.deleteObject(fileName);
	}
}
