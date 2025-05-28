package com.chzzkzzal.zzal.business.application.port.in;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.business.application.port.in.command.UploadCommand;

@Service
public interface UploadZzalUseCase {
	Long upload(UploadCommand command);
}
