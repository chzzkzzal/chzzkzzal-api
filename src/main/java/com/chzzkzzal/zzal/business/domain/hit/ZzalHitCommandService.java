package com.chzzkzzal.zzal.business.domain.hit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.business.application.port.in.AddHitUseCase;
import com.chzzkzzal.zzal.business.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal.business.application.port.out.SaveHitPort;
import com.chzzkzzal.zzal.business.application.port.out.UniqueKeyGenerator;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzalHitCommandService implements AddHitUseCase {
	private final SaveHitPort saveHitPort;
	private final UniqueKeyGenerator keyGen;

	public String addHit(final AddHitCommand command) {
		String key = keyGen.generate(
			command.clientInfo().ipAddress(),
			command.clientInfo().userAgent()
		);
		ZzalHit zzalHit = ZzalHit.of(
			command.zzalId(),
			key,
			command.clientInfo()
		);

		saveHitPort.saveIgnoreDuplicate(zzalHit);
		return key;
	}
}
