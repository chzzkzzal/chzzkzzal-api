package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.model.entity.Zzal;

@Service
public interface ZzalUploadService {
	void upload(String title,Long memberId, MultipartFile multipartFile);
}
