package com.chzzkzzal.core.external.chzzk.domain.model;

public record ChannelInfo(
	String channelId,
	String channelName,
	String channelImageUrl,
	int followerCount
) {
}
