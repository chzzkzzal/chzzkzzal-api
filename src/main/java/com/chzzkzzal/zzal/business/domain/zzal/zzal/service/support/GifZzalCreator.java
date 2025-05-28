package com.chzzkzzal.zzal.business.domain.zzal.zzal.service.support;// GifZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.Gif;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.GifZzal;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

@Component
public class GifZzalCreator implements ZzalCreator {
	@Override
	public boolean supports(MediaMeta metadata) {
		return metadata instanceof Gif;
	}

	@Override
	public Zzal createZzal(SaveZzalCommand command) {
		return GifZzal.create(
			command.channelId(),
			command.member(),
			(Gif)command.mediaMeta(),
			command.title(),
			command.fileUrl()
		);
	}
}
