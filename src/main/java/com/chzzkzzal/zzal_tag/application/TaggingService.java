package com.chzzkzzal.zzal_tag.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.ZzalJpaRepository;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;
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

	public void tagZzal(Long zzalId, List<String> tagNames) {
		Zzal zzal = zzalRepository.findById(zzalId)
			.orElseThrow(() -> new EntityNotFoundException("Zzal not found : " + zzalId));

		List<Hashtag> hashtags = tagNames.stream()
			.map(this::findOrCreateHashtag)
			.collect(Collectors.toList());

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
