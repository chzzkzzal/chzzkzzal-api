package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ZzalUploadService {
	Long upload(String title, Long memberId, MultipartFile multipartFile);
}
