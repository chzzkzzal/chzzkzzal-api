package com.chzzkzzal.core.storage.s3.infratructure.repository;

import java.io.InputStream;
import java.net.URL;

import com.amazonaws.services.s3.model.ObjectMetadata;

public interface S3Repository {
	void putObject(String key, InputStream inputStream, ObjectMetadata metadata);

	URL getFileUrl(String key);

	void deleteObject(String key);

	ObjectMetadata getObjectMetadata(String key);
}
