package com.chzzkzzal.myviewhistory;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyViewHistoryService {
	private final ZzalViewHistoryRepository zzalViewHistoryRepository;

	public void addMemberViewHistory(Long zzalId, Long memberId){
		ZzalViewHistory zzalViewHistory = ZzalViewHistory.addHistory(zzalId, memberId);

		// 1. Select
		boolean exist = zzalViewHistoryRepository.existsByZzalIdAndMemberId(zzalId,memberId);
		// 2. Insert
		if(!exist){
			zzalViewHistoryRepository.save(zzalViewHistory);
		}
	}
}
