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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "치지직 스트리머 API", description = "")
@RequiredArgsConstructor
@RestController
@RequestMapping("/streamers")
public class StreamerController {
	private final StreamerService streamerService;

	@Operation(
		summary = "스트리머 등록",
		description =
			"### 스트리머 등록 \n" +
				"- 1. 치지직 클라이언트 API로 요청을 보내고 스트리머 정보를 응답받는다. \n" +
				"- 참고) 요청시 여러 스트리머 해쉬채널ID를 문자열 배열을 Body에 실는다. \n" +
				"- 2. 스트리머를 DB에 저장한다. \n" +
				"### TODO\n" +
				"- 파일업로드는 별도 api로 분리 \n" +
				"- Body @Validation 추가"
	)
	@PostMapping
	public ResponseEntity<CustomResponse<String>> register(@RequestBody String[] channelIds) {
		String response = streamerService.registerByChzzkClient(channelIds);
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "테스트용 스트리머 등록",
		description =
			"### 직접 스트리머 등록하기 "
	)
	@PostMapping("/v2")
	public ResponseEntity<CustomResponse<String>> register(@RequestBody RegisterStreamerRequest request) {
		String response = streamerService.register(new RegisterStreamerCommand(
			request.channelId(),
			request.channelName(),
			request.channelImageUrl(),
			request.followerCount()
		));
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "모든 스트리머 조회",
		description = ""
	)
	@GetMapping
	public ResponseEntity<CustomResponse<List<GetStreamerResponse>>> findAll() {
		List<GetStreamerResponse> response = streamerService.findAll();
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "스트리머 1명 조회",
		description = ""
	)
	@GetMapping("/{channelId}")
	public ResponseEntity<CustomResponse<GetStreamerResponse>> findById(@PathVariable("channelId") String channelId) {
		GetStreamerResponse response = streamerService.findByChannelId(channelId);
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "특정 스트리머의 짤들 전체 조회",
		description = "### TODO \n" +
			"- 커서 조회방식으로 바꾸기"
	)
	@GetMapping("/{channelId}/zzals")
	public ResponseEntity<CustomResponse<List<ZzalDetailResponse>>> getStreamerZzals(
		@PathVariable("channelId") String channelId) {
		List<ZzalDetailResponse> response = streamerService.getStreamerZzals(channelId);
		return CustomResponse.okResponseEntity(response);
	}

}
