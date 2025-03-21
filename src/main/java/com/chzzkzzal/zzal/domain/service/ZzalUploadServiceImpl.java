package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.domain.dao.FileStoragePort;
import com.chzzkzzal.zzal.domain.model.entity.Zzal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalUploadServiceImpl implements ZzalUploadService {
	// private final ZzalRepository zzalRepository;
	private final FileStoragePort fileStoragePort;

	@Override
	public void upload(Zzal zzal) {
		validate(zzal);
		Zzal upload = zzal.upload();
		fileStoragePort.storeFile(null);
		// saveZzalPort.save(upload);
	}

	private void validate(Zzal zzal) {
		if (zzal == null) {
			throw new IllegalArgumentException("ZZAL은 null일 수 없습니다.");
		}
	}
}
