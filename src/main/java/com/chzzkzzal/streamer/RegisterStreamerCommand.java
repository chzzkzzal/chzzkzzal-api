package com.chzzkzzal.streamer;

public record RegisterStreamerCommand(
	String channelId, String channelName,
	String channelImageUrl, int followerCount) {
}
