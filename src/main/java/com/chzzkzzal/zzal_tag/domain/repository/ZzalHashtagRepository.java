package com.chzzkzzal.zzal_tag.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal_tag.domain.model.Hashtag;
import com.chzzkzzal.zzal_tag.domain.model.ZzalHashtag;
import com.chzzkzzal.zzal_tag.domain.model.ZzalHashtagId;

public interface ZzalHashtagRepository extends JpaRepository<ZzalHashtag, ZzalHashtagId> {

	boolean existsById(ZzalHashtagId id);

	List<ZzalHashtag> findByHashtagIn(Collection<Hashtag> hashtags);

	// LIKE 기반 짤 검색 (distinct + 페이징)
	@Query(value = "select distinct zh.zzal from ZzalHashtag zh where zh.hashtag.name like concat('%', :keyword, '%')")
	Page<Zzal> findZzalsByHashtagKeyword(@Param("keyword") String keyword, Pageable pageable);

	List<ZzalHashtag> findAllByZzal(Zzal zzal);
}
