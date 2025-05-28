package com.chzzkzzal.zzal.infrastructure.hit;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.application.port.out.UniqueKeyGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sha256KeyGenerator implements UniqueKeyGenerator {

	private static final HexFormat HEX = HexFormat.of();

	@Override
	public String generate(String ip, String ua) {

		String seed = (ip == null ? "" : ip) + '|' + (ua == null ? "" : ua);

		try {
			byte[] hash = MessageDigest.getInstance("SHA-256")
				.digest(seed.getBytes(StandardCharsets.UTF_8));
			return HEX.formatHex(hash);
		} catch (NoSuchAlgorithmException ex) {
			log.error("SHA‑256 not available fallback UUID: {}", ex.getMessage());
			return UUID.randomUUID().toString();
		}
	}
}
