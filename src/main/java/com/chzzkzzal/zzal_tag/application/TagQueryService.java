package com.chzzkzzal.zzal_tag.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal.infrastructure.persistence.jpa.ZzalJpaRepository;
import com.chzzkzzal.zzal_tag.domain.model.ZzalHashtag;
import com.chzzkzzal.zzal_tag.domain.repository.ZzalHashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagQueryService {

	private final ZzalHashtagRepository zzalHashtagRepository;
	private final ZzalJpaRepository zzalJpaRepository;

	/**
	 * hashtag.name LIKE '%keyword%' 기반 검색 (페이징 지원)
	 */
	public Page<Zzal> search(String keyword, Pageable pageable) {
		return zzalHashtagRepository.findZzalsByHashtagKeyword(keyword, pageable);
	}

	public List<String> getAllTags(Long zzalId) {
		Zzal zzal = zzalJpaRepository.findById(zzalId)
			.orElseThrow(() -> new IllegalArgumentException());
		List<ZzalHashtag> allByZzal = zzalHashtagRepository.findAllByZzal(zzal);
		List<String> collect = allByZzal.stream()
			.map(hashtag -> hashtag.getHashtag().getName())
			.collect(Collectors.toList());
		return collect;
	}
}
