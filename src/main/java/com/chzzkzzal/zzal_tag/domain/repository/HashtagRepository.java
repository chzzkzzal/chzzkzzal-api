package com.chzzkzzal.zzal_tag.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chzzkzzal.zzal_tag.domain.model.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByName(String name);

	@Query("select h from Hashtag h where h.name like concat('%', :keyword, '%')")
	List<Hashtag> searchByKeyword(@Param("keyword") String keyword);
}
