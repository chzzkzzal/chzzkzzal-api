package com.chzzkzzal.zzal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ZzalUploadService {
	Long upload(String channelId, String title, Long memberId, MultipartFile multipartFile);
}
