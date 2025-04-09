package com.chzzkzzal.zzal_tag.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal_tag.domain.repository.ZzalHashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagQueryService {

	private final ZzalHashtagRepository zzalHashtagRepository;

	/**
	 * hashtag.name LIKE '%keyword%' 기반 검색 (페이징 지원)
	 */
	public Page<Zzal> search(String keyword, Pageable pageable) {
		return zzalHashtagRepository.findZzalsByHashtagKeyword(keyword, pageable);
	}
}
