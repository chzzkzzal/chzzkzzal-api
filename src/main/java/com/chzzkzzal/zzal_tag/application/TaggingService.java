package com.chzzkzzal.zzal_tag.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.infrastructure.persistence.jpa.ZzalJpaRepository;
import com.chzzkzzal.zzal_tag.domain.model.Hashtag;
import com.chzzkzzal.zzal_tag.domain.model.ZzalHashtag;
import com.chzzkzzal.zzal_tag.domain.model.ZzalHashtagId;
import com.chzzkzzal.zzal_tag.domain.repository.HashtagRepository;
import com.chzzkzzal.zzal_tag.domain.repository.ZzalHashtagRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaggingService {

	private final HashtagRepository hashtagRepository;
	private final ZzalJpaRepository zzalRepository;
	private final ZzalHashtagRepository zzalHashtagRepository;

	/**
	 * 짤에 해시태그를 달아준다. (중복 방지 & 배치 insert)
	 */
	public void tagZzal(Long zzalId, List<String> tagNames) {
		Zzal zzal = zzalRepository.findById(zzalId)
			.orElseThrow(() -> new EntityNotFoundException("Zzal not found : " + zzalId));

		// 1) 해시태그 중복 생성 방지
		List<Hashtag> hashtags = tagNames.stream()
			.map(this::findOrCreateHashtag)
			.collect(Collectors.toList());

		// 2) 매핑 엔티티 배치 생성
		List<ZzalHashtag> mappings = hashtags.stream()
			.filter(tag -> !zzalHashtagRepository.existsById(new ZzalHashtagId(zzal.getId(), tag.getId())))
			.map(tag -> new ZzalHashtag(zzal, tag))
			.toList();

		zzalHashtagRepository.saveAll(mappings);
	}

	private Hashtag findOrCreateHashtag(String name) {
		return hashtagRepository.findByName(name)
			.orElseGet(() -> hashtagRepository.save(new Hashtag(name)));
	}
}
