package com.chzzkzzal.zzal.presentation;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.business.application.port.in.query.ExtractMetadataQuery;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.service.MetadataQueryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "MediaFileController", description = "테스트용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mediaFile")
public class MediaFileController {
	private final MetadataQueryService metadataQueryService;

	@PostMapping("tt")
	public Object getMetadata(@RequestParam("files") MultipartFile multipartFiles) {
		try {
			return metadataQueryService.extract(
				new ExtractMetadataQuery(
					multipartFiles.getBytes(),
					multipartFiles.getOriginalFilename(),
					multipartFiles.getContentType())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
