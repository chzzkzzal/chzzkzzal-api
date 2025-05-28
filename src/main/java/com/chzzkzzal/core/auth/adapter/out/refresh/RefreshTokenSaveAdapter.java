package com.chzzkzzal.core.auth.adapter.out.refresh;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.auth.adapter.in.web.exception.RefreshTokenNotFoundException;
import com.chzzkzzal.core.auth.application.port.out.TokenGeneratorPort;
import com.chzzkzzal.core.auth.application.result.TokenResult;
import com.chzzkzzal.core.auth.domain.RefreshToken;
import com.chzzkzzal.core.auth.domain.RefreshTokenStorePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenSaveAdapter implements RefreshTokenStorePort {
	private final TokenGeneratorPort tokenGeneratorPort;
	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken getRefreshTokenByValue(String value) {
		return refreshTokenRepository
			.findByToken(value)
			.orElseThrow(RefreshTokenNotFoundException::new);
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public Optional<RefreshToken> findById(final String s) {
		return refreshTokenRepository.findByToken(s);
	}

	@Override
	public void save(final RefreshToken refreshToken) {
		saveOrUpdateRefreshToken(refreshToken);
	}

	public TokenResult rotateRefreshToken(RefreshToken refreshToken) {
		String reissuedToken = rotate(refreshToken);
		return new TokenResult(reissuedToken, refreshToken.getExternalId());
	}

	private String rotate(RefreshToken refreshToken) {
		String reissuedToken = tokenGeneratorPort.generateRefreshToken(refreshToken.getExternalId());
		RefreshToken rotatedToken = refreshToken.rotate(reissuedToken);
		refreshTokenRepository.save(rotatedToken);
		return reissuedToken;
	}

	private void saveOrUpdateRefreshToken(RefreshToken refreshToken) {
		refreshTokenRepository.findById(refreshToken.getExternalId())
			.ifPresentOrElse(
				it -> it.rotate(refreshToken.getToken()),
				() -> refreshTokenRepository.save(refreshToken)
			);
	}

	@Transactional
	public void expireRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
		SecurityContextHolder.clearContext();
	}

}
