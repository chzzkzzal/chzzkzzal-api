package com.chzzkzzal.zzal_view_log;

import static com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer.*;

import java.time.LocalDateTime;

import com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer;

public record ZzalViewLogDto(Long zzalId, Long memberId, String uniqueIdentifier, String ipAddress, String userAgent,
							 BrowserType browserType, DeviceType deviceType, LocalDateTime localDateTime
) {

	public  ZzalViewLog toEntity(){
		return  ZzalViewLog.addViewLog(zzalId,memberId,uniqueIdentifier,ipAddress,userAgent,browserType,deviceType,localDateTime);
	}
}
