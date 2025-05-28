package com.chzzkzzal.zzal.business.application.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.out.MemberHistoryPort;
import com.chzzkzzal.zzal.business.application.port.out.UniqueKeyGenerator;
import com.chzzkzzal.zzal.business.application.port.out.ViewLogPort;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZzalViewLogHandler {

	private final ViewLogPort viewLogPort;
	private final MemberHistoryPort historyPort;
	private final UniqueKeyGenerator keyGen;

	@EventListener
	public void on(ZzalViewedEvent e) {

		String uniqueKey = keyGen.generate(
			e.clientInfo().ipAddress(),
			e.clientInfo().userAgent()
		);

		viewLogPort.save(new ZzalViewLogDto(
			e.zzalId(), e.memberId(), uniqueKey,
			e.clientInfo().ipAddress(),
			e.clientInfo().userAgent(),
			e.clientInfo().browserType(),
			e.clientInfo().deviceType(),
			LocalDateTime.now()
		));

		if (e.memberId() != null) {
			historyPort.addMemberHistory(e.zzalId(), e.memberId());
		}
	}
}
