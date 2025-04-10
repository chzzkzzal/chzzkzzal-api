package com.chzzkzzal.streamer;

public record RegisterStreamerRequest(
	String channelId, String channelName,
	String channelImageUrl, int followerCount) {
}
