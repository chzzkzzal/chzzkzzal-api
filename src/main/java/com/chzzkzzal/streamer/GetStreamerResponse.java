package com.chzzkzzal.streamer;

import java.time.LocalDateTime;

public record GetStreamerResponse(
	Long streamerId,
	String channelId, String channelName,
	String channelImageUrl, int followerCount,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}
