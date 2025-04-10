package com.chzzkzzal.zzalhits.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzalhits.domain.ZzalHits;
import com.chzzkzzal.zzalhits.repository.ZzalHitsRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalHitsService {
	private final ZzalHitsRepository zzalHitsRepository;


	public ZzalHits addHits(Long zzalId, HttpServletRequest request){
		ZzalHits zzalHits = ZzalHits.addFromRequest(zzalId, request);

		// 1. Select
		boolean exists = zzalHitsRepository.existsByUniqueIdentifier(zzalHits.getUniqueIdentifier());
		// 2. Insert
		if (!exists) {
			zzalHitsRepository.save(zzalHits);
		}
		return zzalHits;
	}

	// 주의할점은 클라이언트는 모두 서로 다른 클라이언트여야만함.
	public void insert(Long zzalId, HttpServletRequest request){
		ZzalHits zzalHits = ZzalHits.addFromRequest(zzalId, request);

		zzalHitsRepository.save(zzalHits);

	}

	// 해당 게시글의 카운트 집계하기 . 이때 여러 게시글이 존재하고, 해당 게시글 마다 조회가 삽입되어 있어야함.
	public Long count(Long zzalId){

		Long aLong = zzalHitsRepository.countByZzalId(zzalId);
		return aLong;
	}
}
