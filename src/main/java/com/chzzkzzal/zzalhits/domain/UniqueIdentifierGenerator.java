package com.chzzkzzal.zzalhits.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniqueIdentifierGenerator {

	public static String generate(String ipAddress, String userAgent) {
		// 입력 유효성 검사
		String safeIp = ipAddress != null ? ipAddress : "";
		String safeUserAgent = userAgent != null ? userAgent : "";
		String combinedString = safeIp + "|" + safeUserAgent;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(combinedString.getBytes(StandardCharsets.UTF_8));

			// 해시를 16진수 문자열로 변환
			StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
			for (byte b : encodedHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("uniqueIdentifier error: {}",e.getMessage());
			return UUID.randomUUID().toString();
		}
	}

}
