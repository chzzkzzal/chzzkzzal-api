package com.chzzkzzal.zzalhits.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzalhits.domain.ZzalHits;

public interface ZzalHitsRepository extends JpaRepository<ZzalHits,Long> {

	Optional<ZzalHits> findByUniqueIdentifier(String uniqueIdentifier);
	boolean existsByUniqueIdentifier(String uniqueIdentifier);

	Long countByZzalId(Long zzalId);

}
