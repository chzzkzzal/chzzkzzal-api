package com.chzzkzzal.zzal_view_log;

import static com.chzzkzzal.zzal_view_log.QZzalViewLog.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ZzalViewLogQueryDsl implements  ZzalViewLogRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	public List<ZzalRepeatCountDto> findTop5ByRepeatViews() {

		// 1) 전체 방문수
		NumberExpression<Long> totalViews = zzalViewLog.id.count();

		// 2) 유니크 방문자 수 (uniqueIdentifier 기준)
		NumberExpression<Long> distinctUsers = zzalViewLog.uniqueIdentifier.countDistinct();

		// 3) 중복(반복) 방문수 = totalViews - distinctUsers
		NumberExpression<Long> repeatedViews = totalViews.subtract(distinctUsers);

		return queryFactory
			.select(Projections.constructor(
				ZzalRepeatCountDto.class,
				zzalViewLog.zzalId,
				repeatedViews
			))
			.from(zzalViewLog)
			.groupBy(zzalViewLog.zzalId)
			.orderBy(repeatedViews.desc()) // 중복 방문이 많은 순
			.limit(5)
			.fetch();
	}

}
