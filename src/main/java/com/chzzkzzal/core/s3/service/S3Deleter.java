package com.chzzkzzal.core.s3.service;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.s3.S3Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class S3Deleter {

	private final S3Repository s3Repository;

	public void delete(String fileName) {
		s3Repository.deleteObject(fileName);
	}
}
