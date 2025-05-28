package com.chzzkzzal.core.auth.domain;

import java.util.Optional;

import com.chzzkzzal.core.auth.application.result.TokenResult;

public interface RefreshTokenStorePort {

	void save(RefreshToken refreshToken);

	RefreshToken getRefreshTokenByValue(String value);

	TokenResult rotateRefreshToken(RefreshToken refreshToken);

	Optional<RefreshToken> findById(String s);

	void expireRefreshToken(String tokenString);

}
