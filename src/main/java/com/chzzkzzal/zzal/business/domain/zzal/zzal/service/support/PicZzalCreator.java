package com.chzzkzzal.zzal.business.domain.zzal.zzal.service.support;// PicZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.Pic;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.PicZzal;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

@Component
public class PicZzalCreator implements ZzalCreator {
	@Override
	public boolean supports(MediaMeta metadata) {
		return metadata instanceof Pic;
	}

	@Override
	public Zzal createZzal(SaveZzalCommand command) {
		return PicZzal.create(
			command.channelId(),
			command.member(),
			(Pic)command.mediaMeta(),
			command.title(),
			command.fileUrl()
		);
	}
}
