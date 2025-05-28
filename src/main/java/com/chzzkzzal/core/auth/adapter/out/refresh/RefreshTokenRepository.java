package com.chzzkzzal.core.auth.adapter.out.refresh;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.core.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
	Optional<RefreshToken> findByToken(String token);

	void deleteByToken(String token);
}
