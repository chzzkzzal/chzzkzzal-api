package com.chzzkzzal.streamer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.error.CustomResponse;
import com.chzzkzzal.zzal.domain.service.ZzalDetailResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/streamers")
public class StreamerController {
	private final StreamerService streamerService;

	@PostMapping
	public ResponseEntity<CustomResponse<Long>> register(@RequestBody RegisterStreamerRequest request) {
		Long response = streamerService.register(new RegisterStreamerCommand(
			request.channelId(),
			request.channelName(),
			request.channelImageUrl(),
			request.followerCount()
		));
		return CustomResponse.okResponseEntity(response);
	}

	@GetMapping
	public ResponseEntity<CustomResponse<List<GetStreamerResponse>>> findAll() {
		List<GetStreamerResponse> response = streamerService.findAll();
		return CustomResponse.okResponseEntity(response);
	}

	@GetMapping("/{streamerId}")
	public ResponseEntity<CustomResponse<GetStreamerResponse>> findById(@PathVariable("streamerId") Long streamerId) {
		GetStreamerResponse response = streamerService.findById(streamerId);
		return CustomResponse.okResponseEntity(response);
	}

	@GetMapping("/{streamerId}/zzals")
	public ResponseEntity<CustomResponse<List<ZzalDetailResponse>>> getStreamerZzals(
		@PathVariable("streamerId") Long streamerId) {
		List<ZzalDetailResponse> response = streamerService.getStreamerZzals(streamerId);
		return CustomResponse.okResponseEntity(response);
	}

}
