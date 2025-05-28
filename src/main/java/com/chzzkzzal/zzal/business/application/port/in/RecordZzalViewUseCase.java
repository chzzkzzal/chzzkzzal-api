package com.chzzkzzal.zzal.business.application.port.in;

import com.chzzkzzal.zzal.business.application.port.in.command.RecordZzalViewCommand;

public interface RecordZzalViewUseCase {
	void record(RecordZzalViewCommand cmd);
}
