package com.chzzkzzal.core.chzzk.common.response;

public record ChzzkChannelInfoResponse(
	String channelId,
	String channelName,
	String channelImageUrl,
	int followerCount
) {
}
