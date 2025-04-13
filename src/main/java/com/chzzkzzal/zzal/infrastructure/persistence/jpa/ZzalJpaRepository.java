package com.chzzkzzal.zzal.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {

	List<Zzal> findAllByChannelId(String channelId);
}
