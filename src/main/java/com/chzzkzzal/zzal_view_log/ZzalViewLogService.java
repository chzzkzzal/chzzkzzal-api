package com.chzzkzzal.zzal_view_log;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ZzalViewLogService {
	private final ZzalViewLogRepository zzalViewLogRepository;
	private final ZzalViewLogRepositoryCustom zzalViewLogRepositoryCustom;

	public void addViewLog(ZzalViewLogDto zzalViewLogDto){
		ZzalViewLog zzalViewLog = zzalViewLogDto.toEntity();
		zzalViewLogRepository.save(zzalViewLog);
	}

	public List<ZzalRepeatCountDto> getTop5RepeatedZzal() {
		return zzalViewLogRepositoryCustom.findTop5ByRepeatViews();
	}
}
