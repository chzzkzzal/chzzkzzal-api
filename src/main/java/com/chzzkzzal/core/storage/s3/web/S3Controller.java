package com.chzzkzzal.core.storage.s3.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.application.S3ServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "S3 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3Controller {

	private final S3ServicePort s3ServicePort;

	@Operation(
		summary = "파일들 S3업로드",
		description = ""
	)
	@PostMapping
	public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> multipartFiles) {
		return ResponseEntity.ok(s3ServicePort.uploadFiles(multipartFiles));
	}

	@Operation(
		summary = "S3 파일 제거",
		description = ""
	)
	@DeleteMapping
	public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
		s3ServicePort.deleteFile(fileName);
		return ResponseEntity.ok(fileName);
	}

	@Operation(
		summary = "S3 파일 링크 다운로드",
		description = ""
	)
	@GetMapping
	public ResponseEntity<String> downloadFile(@RequestParam(value = "fileName") String fileName) {
		String fileUrl = s3ServicePort.getFileUrl(fileName);
		return ResponseEntity.ok(fileUrl);
	}

}
