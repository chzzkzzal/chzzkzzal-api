package com.chzzkzzal.zzal.infrastructure.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.error.CustomResponse;
import com.chzzkzzal.zzal.domain.service.ZzalDetailResponse;
import com.chzzkzzal.zzal.domain.service.ZzalDetailService;
import com.chzzkzzal.zzal.domain.service.ZzalGetAllService;
import com.chzzkzzal.zzal.domain.service.ZzalUploadService;
import com.chzzkzzal.zzal.infrastructure.dto.ZzalCreateRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zzals")
public class ZzalController {

	private final ZzalUploadService zzalUploadService;
	private final ZzalDetailService zzalDetailService;
	private final ZzalGetAllService zzalGetAllService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CustomResponse<Long>> upload(@RequestPart(value = "file") MultipartFile multipartFile,
		@RequestPart(value = "zzalCreateRequest") ZzalCreateRequest zzalCreateRequest) {
		Long memberId = Long.valueOf(1);
		Long response = zzalUploadService.upload(zzalCreateRequest.streamerId(), zzalCreateRequest.title(), memberId,
			multipartFile);
		return CustomResponse.okResponseEntity(response);
	}

	@GetMapping("{zzalId}")
	public ResponseEntity<CustomResponse<ZzalDetailResponse>> viewDetail(
		@PathVariable("zzalId") Long zzalId, HttpServletRequest request) {

		// Long memberId = Long.valueOf(1);
		ZzalDetailResponse response = zzalDetailService.getZZal(zzalId, request);
		return CustomResponse.okResponseEntity(response);
	}

	@GetMapping
	public ResponseEntity<CustomResponse<List<ZzalDetailResponse>>> getAll() {
		List<ZzalDetailResponse> responses = zzalGetAllService.getAll();
		return CustomResponse.okResponseEntity(responses);
	}
}
