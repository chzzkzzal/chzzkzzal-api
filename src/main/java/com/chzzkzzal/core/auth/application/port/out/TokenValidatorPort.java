package com.chzzkzzal.core.auth.application.port.out;

public interface TokenValidatorPort {
	void validate(String jwt);

	boolean isValid(String jwt);
}
