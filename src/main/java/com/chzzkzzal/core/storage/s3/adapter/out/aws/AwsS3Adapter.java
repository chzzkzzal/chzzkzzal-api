package com.chzzkzzal.core.storage.s3.adapter.out.aws;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chzzkzzal.common.properties.S3Properties;
import com.chzzkzzal.core.storage.s3.adapter.out.aws.internal.FileNameGenerator;
import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;
import com.chzzkzzal.core.storage.s3.application.port.StoragePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwsS3Adapter implements StoragePort {

	private final AmazonS3 s3;
	private final S3Properties prop;
	private final FileNameGenerator nameGenerator;

	@Override
	public List<String> upload(List<UploadFileCommand> files) {
		return files.stream().map(this::upload).toList();
	}

	@Override
	public String upload(UploadFileCommand file) {
		String key = nameGenerator.generate(file.originalName());

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(file.bytes().length);
		meta.setContentType(file.contentType());

		try (InputStream is = file.inputStream()) {
			s3.putObject(new PutObjectRequest(prop.bucket(), key, is, meta)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			throw new IllegalStateException("S3 upload error", e);
		}
		return key;
	}

	@Override
	public void delete(String key) {
		s3.deleteObject(new DeleteObjectRequest(prop.bucket(), key));
	}

	@Override
	public URL getUrl(String key) {
		return s3.getUrl(prop.bucket(), key);
	}
}
