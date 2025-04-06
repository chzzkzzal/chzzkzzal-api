package com.chzzkzzal.zzal.infrastructure.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.auth.domain.MemberUserDetails;
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
	public void upload(@RequestPart(value = "file") MultipartFile multipartFile,
		@RequestPart(value = "zzalCreateRequest") ZzalCreateRequest zzalCreateRequest) {
		Long memberId = Long.valueOf(1);
		zzalUploadService.upload(zzalCreateRequest.title(),memberId, multipartFile);
	}

	@GetMapping("{zzalId}")
	public ZzalDetailResponse viewDetail(@AuthenticationPrincipal MemberUserDetails memberUserDetails,
		@PathVariable("zzalId") Long zzalId, HttpServletRequest request) {
		Long memberId = memberUserDetails != null ? memberUserDetails.getMember().getId() : null;

		// Long memberId = Long.valueOf(1);
		return zzalDetailService.getZZal(memberId, zzalId,request);
	}

	@GetMapping
	public List<ZzalDetailResponse> getAll() {
		return zzalGetAllService.getAll();
	}
}
