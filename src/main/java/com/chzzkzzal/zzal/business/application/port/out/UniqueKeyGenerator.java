package com.chzzkzzal.zzal.business.application.port.out;

public interface UniqueKeyGenerator {
	String generate(String ipAddress, String userAgent);

}
