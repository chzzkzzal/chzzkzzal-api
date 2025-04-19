package com.chzzkzzal.zzal_view_log;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.common.error.CustomResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "(미구현) 짤 로그 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class ZzalViewLogController {
	private final ZzalViewLogService zzalViewLogService;

	@GetMapping("/top5")
	public ResponseEntity<CustomResponse<List<ZzalRepeatCountDto>>> getTop5() {
		List<ZzalRepeatCountDto> response = zzalViewLogService.getTop5RepeatedZzal();
		return CustomResponse.okResponseEntity(response);
	}
}
