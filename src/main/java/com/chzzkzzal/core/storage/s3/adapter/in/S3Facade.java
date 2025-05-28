package com.chzzkzzal.core.storage.s3.adapter.in;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;
import com.chzzkzzal.core.storage.s3.application.service.StorageFacadeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Facade {

	private final StorageFacadeService service;

	public List<String> uploadFiles(List<UploadFileCommand> files) {
		return service.uploadFiles(files);
	}

	public String uploadFile(UploadFileCommand command) {
		return service.uploadFile(command);
	}

	public void deleteFile(String key) {
		service.deleteFile(key);
	}

	public String getFileUrl(String key) {
		return service.getFileUrl(key);
	}
}
