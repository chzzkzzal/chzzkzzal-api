package com.chzzkzzal.zzal.business.domain.zzal.zzal.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.business.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.business.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.service.support.ZzalCreatorResolver;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalCommandService {
	private final SaveZzalPort saveZzalPort;
	private final ZzalCreatorResolver zzalCreatorResolver;

	public Long save(SaveZzalCommand command) {
		Zzal zzal = zzalCreatorResolver
			.getFactory(command.mediaMeta())
			.createZzal(command);

		return saveZzalPort
			.save(zzal)
			.getId();
	}
}
