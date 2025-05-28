package com.chzzkzzal.core.chzzk.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChzzkUserResponse(
	@JsonProperty("channelId")
	String channelId,

	@JsonProperty("channelName")
	String channelName
) {
}
