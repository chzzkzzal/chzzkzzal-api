package com.chzzkzzal.zzal;

import static com.chzzkzzal.zzal.domain.service.ZzalDetailServiceImpl.*;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.chzzkzzal.myviewhistory.MyViewHistoryService;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;
import com.chzzkzzal.zzal_view_log.ZzalViewLogService;
import com.chzzkzzal.zzalhits.domain.ZzalHits;
import com.chzzkzzal.zzalhits.service.ZzalHitsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalViewedEventHandler {
	private final ZzalHitsService zzalHitsService;
	private final MyViewHistoryService myViewHistoryService;
	private final ZzalViewLogService zzalViewLogService;

	@EventListener(ZzalViewedEvent.class)
	// @TransactionalEventListener(
	// 	classes = ZzalViewedEvent.class,
	// 	phase = TransactionPhase.AFTER_COMMIT
	// )
	public void handle(ZzalViewedEvent event){
		Long zzalId = event.zzalId();
		HttpServletRequest request = event.httpServletRequest();
		Long memberId = event.memberId();
		ZzalHits zzalHits = zzalHitsService.addHits(zzalId, request);
		zzalViewLogService.addViewLog(getZzalViewLogDto(memberId, zzalId, zzalHits));
		if (memberId != null) {
			myViewHistoryService.addMemberViewHistory(zzalId, memberId);
		}
	}
	private static ZzalViewLogDto getZzalViewLogDto(Long memberId, Long zzalId, ZzalHits zzalHits) {
		return new ZzalViewLogDto(
			zzalId, memberId, zzalHits.getUniqueIdentifier(), zzalHits.getIpAddress(), zzalHits.getUserAgent(),
			zzalHits.getBrowserType(), zzalHits.getDeviceType(),
			LocalDateTime.now());
	}
}
