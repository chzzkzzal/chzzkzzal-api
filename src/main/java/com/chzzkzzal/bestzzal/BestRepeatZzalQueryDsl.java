package com.chzzkzzal.bestzzal;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BestRepeatZzalQueryDsl {
	private final JPAQueryFactory jpaQueryFactory;
}
