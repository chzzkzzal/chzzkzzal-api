package com.chzzkzzal.zzal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.domain.model.entity.Zzal;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {
}
