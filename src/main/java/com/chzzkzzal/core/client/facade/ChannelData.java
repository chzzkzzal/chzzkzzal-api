package com.chzzkzzal.core.client.facade;

public record ChannelData(
	String channelId,
	String channelName,
	String channelImageUrl,
	int followerCount
) {
}
