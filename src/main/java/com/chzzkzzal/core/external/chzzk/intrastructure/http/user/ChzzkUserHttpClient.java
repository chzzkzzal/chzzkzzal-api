package com.chzzkzzal.core.external.chzzk.intrastructure.http.user;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.chzzkzzal.core.external.chzzk.application.port.ChzzkUserPort;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequest;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequestFactory;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkRestExecutor;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChzzkUserHttpClient implements ChzzkUserPort {

	private static final String USER_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";
	private final ChzzkHttpRequestFactory factory;
	private final ChzzkRestExecutor exec;

	@Override
	public ChzzkUserResponse me(String accessToken) {
		String full = UriComponentsBuilder.fromHttpUrl(USER_INFO_URL)
			.toUriString();

		ChzzkHttpRequest req = factory
			.base(GET, full)
			.addHeader(AUTHORIZATION.getDisplayName(), BEARER_SPACEBAR.getDisplayName() + accessToken);

		return exec.exchange(req, ChzzkUserResponse.class);

	}
}
