package com.chzzkzzal.core.auth.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class RefreshToken {
	@Id
	private String externalId;

	@Column(nullable = false, length = 512)
	private String token;

	public static RefreshToken of(String externalId, String token) {
		return RefreshToken.builder()
			.externalId(externalId)
			.token(token)
			.build();
	}

	public RefreshToken rotate(String newRefreshToken) {
		this.token = newRefreshToken;
		return new RefreshToken(this.externalId, newRefreshToken);
	}

	public boolean validateRefreshToken(String refreshToken) {
		return this.token.equals(refreshToken);
	}
}
