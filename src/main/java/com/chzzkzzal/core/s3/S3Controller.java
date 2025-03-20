package com.chzzkzzal.core.s3;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3Controller {

	private final S3Service s3Service;

	@PostMapping
	public ResponseEntity<List<String>> uploadFile(List<MultipartFile> multipartFiles) {
		return ResponseEntity.ok(s3Service.uploadFile(multipartFiles));
	}

	@DeleteMapping
	public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
		s3Service.deleteFile(fileName);
		return ResponseEntity.ok(fileName);
	}
}
