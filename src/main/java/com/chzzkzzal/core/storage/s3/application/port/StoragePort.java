package com.chzzkzzal.core.storage.s3.application.port;

import java.net.URL;
import java.util.List;

import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;

public interface StoragePort {

	List<String> upload(List<UploadFileCommand> commands);

	String upload(UploadFileCommand command);

	void delete(String key);

	URL getUrl(String key);
}
