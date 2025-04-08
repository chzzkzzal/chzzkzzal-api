package com.chzzkzzal.streamer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamerService {
	private final StreamerRepository streamerRepository;
	
	public void register(RegisterStreamerCommand command) {
		Streamer streamer = Streamer.register(
			command.channelId(),
			command.channelName(),
			command.channelImageUrl(),
			command.followerCount()
		);
		streamerRepository.save(streamer);
	}

	public List<GetStreamerResponse> findAll() {
		return streamerRepository.findAll().stream().map(streamer -> new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		)).collect(Collectors.toList());
	}

	public GetStreamerResponse findById(Long streamerId) {
		Streamer streamer = streamerRepository.findById(streamerId).orElseThrow(() -> new IllegalArgumentException());
		return new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		);

	}
}
