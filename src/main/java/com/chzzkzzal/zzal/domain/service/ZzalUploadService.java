package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.domain.model.Zzal;

@Service
public interface ZzalUploadService {
	void upload(Zzal zzal);
}
