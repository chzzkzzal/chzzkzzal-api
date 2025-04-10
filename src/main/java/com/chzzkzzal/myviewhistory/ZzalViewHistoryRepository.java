package com.chzzkzzal.myviewhistory;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.myviewhistory.ZzalViewHistory;

@Repository
public interface ZzalViewHistoryRepository extends JpaRepository<ZzalViewHistory,Long> {

	boolean existsByZzalIdAndMemberId(Long zzalId, Long memberId);
}
