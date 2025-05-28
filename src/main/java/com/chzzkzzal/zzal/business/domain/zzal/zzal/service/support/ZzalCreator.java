package com.chzzkzzal.zzal.business.domain.zzal.zzal.service.support;

import com.chzzkzzal.zzal.business.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

// ZzalCreator.java
public interface ZzalCreator {
	boolean supports(MediaMeta metadata);

	Zzal createZzal(SaveZzalCommand saveZzalCommand);
}

