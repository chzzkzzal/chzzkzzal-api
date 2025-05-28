package com.chzzkzzal.zzal.business.domain.zzal.zzal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {

	List<Zzal> findAllByChannelId(String channelId);
}
