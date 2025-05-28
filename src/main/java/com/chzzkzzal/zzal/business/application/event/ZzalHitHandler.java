package com.chzzkzzal.zzal.business.application.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.in.AddHitUseCase;
import com.chzzkzzal.zzal.business.application.port.in.command.AddHitCommand;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZzalHitHandler {

	private final AddHitUseCase addHitUseCase;

	@EventListener
	public void on(ZzalViewedEvent e) {
		if (!e.countable())
			return;

		addHitUseCase.addHit(new AddHitCommand(e.zzalId(), e.clientInfo()));
	}
}
