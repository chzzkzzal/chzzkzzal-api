package com.chzzkzzal.zzal.infrastructure.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.MetadataProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mediaFile")
public class MediaFileController {
	private final MetadataProvider metadataProvider;

	@PostMapping("tt")
	public Object getMetadata(@RequestParam("files") MultipartFile multipartFiles) {
		return metadataProvider.getMetadata(multipartFiles);
	}
}
