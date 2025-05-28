package com.chzzkzzal.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record FrontProperties(
	@Value("${chzzkzzal.front}") String domain
) {
}
