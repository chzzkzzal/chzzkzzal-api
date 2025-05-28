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
public class ZzalViewLogQueryDsl implements ZzalViewLogRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public List<ZzalRepeatCountDto> findTop5ByRepeatViews() {

		NumberExpression<Long> totalViews = zzalViewLog.id.count();

		NumberExpression<Long> distinctUsers = zzalViewLog.uniqueIdentifier.countDistinct();

		NumberExpression<Long> repeatedViews = totalViews.subtract(distinctUsers);

		return queryFactory
			.select(Projections.constructor(
				ZzalRepeatCountDto.class,
				zzalViewLog.zzalId,
				repeatedViews
			))
			.from(zzalViewLog)
			.groupBy(zzalViewLog.zzalId)
			.orderBy(repeatedViews.desc())
			.limit(5)
			.fetch();
	}

}
