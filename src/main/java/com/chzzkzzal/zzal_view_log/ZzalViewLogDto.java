package com.chzzkzzal.zzal_view_log;

import static com.chzzkzzal.zzal.infrastructure.hit.UserAgentAnalyzer.*;

import java.time.LocalDateTime;

public record ZzalViewLogDto(Long zzalId, Long viewerId, String uniqueIdentifier, String ipAddress, String userAgent,
							 BrowserType browserType, DeviceType deviceType, LocalDateTime localDateTime
) {

	public ZzalViewLog toEntity() {
		return ZzalViewLog.addViewLog(zzalId, viewerId, uniqueIdentifier, ipAddress, userAgent, browserType, deviceType,
			localDateTime);
	}
}
