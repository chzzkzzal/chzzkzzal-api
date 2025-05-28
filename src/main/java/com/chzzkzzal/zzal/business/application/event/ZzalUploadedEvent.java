package com.chzzkzzal.zzal.business.application.event;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.ZzalType;

public record ZzalUploadedEvent(Long zzalId, ZzalType type, String filePath) {
}
