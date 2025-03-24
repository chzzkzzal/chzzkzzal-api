package com.chzzkzzal.zzal.infrastructure.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.entity.Zzal;
import com.chzzkzzal.zzal.domain.service.ZzalDetailResponse;
import com.chzzkzzal.zzal.domain.service.ZzalDetailService;
import com.chzzkzzal.zzal.domain.service.ZzalGetAllService;
import com.chzzkzzal.zzal.domain.service.ZzalUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zzals")
public class ZzalController {

	private final ZzalUploadService zzalUploadService;
	private final ZzalDetailService zzalDetailService;
	private final ZzalGetAllService zzalGetAllService;

	@PostMapping
	public void upload(@RequestParam(value = "file") MultipartFile multipartFile) {
		Long memberId = Long.valueOf(1);
		zzalUploadService.upload(memberId, multipartFile);
	}

	@GetMapping("{zzalId}")
	public ZzalDetailResponse loadDetail(@PathVariable("zzalId") Long zzalId) {
		Long memberId = Long.valueOf(1);
		return zzalDetailService.loadDetail(memberId, zzalId);
	}

	@GetMapping()
	public List<ZzalDetailResponse> getAll() {
		return zzalGetAllService.getAll();
	}
}
