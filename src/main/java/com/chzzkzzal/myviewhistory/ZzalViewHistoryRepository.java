package com.chzzkzzal.myviewhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzalViewHistoryRepository extends JpaRepository<ZzalViewHistory, Long> {

	boolean existsByZzalIdAndMemberId(Long zzalId, Long memberId);
}
