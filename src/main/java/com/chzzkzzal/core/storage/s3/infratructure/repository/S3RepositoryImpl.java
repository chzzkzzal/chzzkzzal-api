package com.chzzkzzal.core.storage.s3.infratructure.repository;

import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chzzkzzal.core.storage.properties.S3Properties;
import com.chzzkzzal.core.storage.s3.domain.S3Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class S3RepositoryImpl implements S3Repository {

	private final AmazonS3 amazonS3;

	private final S3Properties s3Properties;

	@Override
	public void putObject(String key, InputStream inputStream, ObjectMetadata metadata) {
		amazonS3.putObject(new PutObjectRequest(s3Properties.bucket(), key, inputStream, metadata)
			.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	@Override
	public URL getFileUrl(String key) {
		return amazonS3.getUrl(s3Properties.bucket(), key);
	}

	@Override
	public void deleteObject(String key) {
		amazonS3.deleteObject(new DeleteObjectRequest(s3Properties.bucket(), key));
	}

}
