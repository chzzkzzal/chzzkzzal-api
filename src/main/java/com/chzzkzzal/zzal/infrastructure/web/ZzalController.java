package com.chzzkzzal.zzal.infrastructure.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.service.ZzalUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zzals")
public class ZzalController {

	private final ZzalUploadService zzalUploadService;

	@PostMapping
	public void upload(@RequestParam(value= "file")MultipartFile multipartFile){
		Long memberId = Long.valueOf(1);
		zzalUploadService.upload(memberId,multipartFile);
	}
}
