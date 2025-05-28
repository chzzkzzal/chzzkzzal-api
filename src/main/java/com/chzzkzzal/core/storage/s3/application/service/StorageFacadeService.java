package com.chzzkzzal.core.storage.s3.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;
import com.chzzkzzal.core.storage.s3.application.port.StoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageFacadeService {

	private final StoragePort storage;

	public List<String> uploadFiles(List<UploadFileCommand> commands) {

		return storage.upload(commands);
	}

	public String uploadFile(UploadFileCommand command) {

		return storage.upload(command);
	}

	public void deleteFile(String key) {
		storage.delete(key);
	}

	public String getFileUrl(String key) {
		return storage.getUrl(key).toString();
	}
}
