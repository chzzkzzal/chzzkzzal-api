package com.chzzkzzal.zzal.business.application.port.out;

import com.chzzkzzal.zzal.business.domain.hit.ZzalHit;

public interface SaveHitPort {
	void saveIgnoreDuplicate(ZzalHit hit);

}
