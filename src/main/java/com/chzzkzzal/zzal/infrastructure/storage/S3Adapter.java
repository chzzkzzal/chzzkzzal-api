package com.chzzkzzal.zzal.infrastructure.storage;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.storage.s3.adapter.in.S3Facade;
import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;
import com.chzzkzzal.zzal.business.application.port.out.FileStoragePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Adapter implements FileStoragePort {
	private final S3Facade s3;

	public String upload(UploadFileCommand command) {
		return s3.uploadFile(command);
	}

	public String getUrl(String url) {
		return s3.getFileUrl(url);
	}
}
