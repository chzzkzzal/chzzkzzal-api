package com.chzzkzzal.myviewhistory;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chzzkzzal.myviewhistory.ZzalViewHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberViewZzalHistoryQueryDsl {
	private final JPAQueryFactory queryFactory;


	// 최근 본 짤 목록 조회 (QueryDSL 사용)
	public List<ZzalViewHistory> findRecentViewedZzals(Long memberId, int limit) {
		QZzalViewHistory qZzalViewHistory = QZzalViewHistory.zzalViewHistory;

		return queryFactory
			.selectFrom(qZzalViewHistory)
			.where(qZzalViewHistory.memberId.eq(memberId))
			.orderBy(qZzalViewHistory.viewDateTime.desc())
			.limit(limit)
			.fetch();
	}
}
