package com.chzzkzzal.core.auth.application.port.out;

public interface TokenGeneratorPort {

	String generateAccessToken(String externalId);

	String generateRefreshToken(String externalId);
}
