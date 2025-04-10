package com.chzzkzzal.streamer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/streamers")
public class StreamerController {
	private final StreamerService streamerService;

	@PostMapping
	public void register(@RequestBody RegisterStreamerRequest request) {
		streamerService.register(new RegisterStreamerCommand(
			request.channelId(),
			request.channelName(),
			request.channelImageUrl(),
			request.followerCount()
		));
	}

	@GetMapping
	public List<GetStreamerResponse> findAll() {
		return streamerService.findAll();
	}

	@GetMapping("/{streamerId}")
	public GetStreamerResponse findById(@PathVariable("streamerId") Long streamerId) {
		return streamerService.findById(streamerId);
	}
}
