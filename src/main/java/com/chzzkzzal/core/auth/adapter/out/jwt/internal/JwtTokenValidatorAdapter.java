package com.chzzkzzal.core.auth.adapter.out.jwt.internal;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.adapter.in.web.exception.InvalidJwtTokenException;
import com.chzzkzzal.core.auth.adapter.out.jwt.JwtSigningKeyProvider;
import com.chzzkzzal.core.auth.application.port.out.TokenValidatorPort;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidatorAdapter implements TokenValidatorPort {
	private final JwtSigningKeyProvider keyProvider;

	@Override
	public void validate(final String jwt) {
		SecretKey signingKey = keyProvider.getSigningKey();
		try {
			Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(jwt);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new InvalidJwtTokenException();
		}
	}

	@Override
	public boolean isValid(final String jwt) {
		try {
			validate(jwt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
