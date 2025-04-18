package com.chzzkzzal.core.external.chzzk.application.port;

import com.chzzkzzal.core.external.chzzk.domain.model.ChannelInfo;

public interface ChzzkChannelPort {
	ChannelInfo fetchChannelInfo(String[] channelIds);
}
