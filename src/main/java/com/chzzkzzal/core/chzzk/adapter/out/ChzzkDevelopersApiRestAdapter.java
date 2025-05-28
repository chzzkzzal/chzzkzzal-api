package com.chzzkzzal.core.chzzk.adapter.out;

import static com.chzzkzzal.core.chzzk.common.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.chzzkzzal.core.chzzk.adapter.out.core.ChzzkHttpRequestFactory;
import com.chzzkzzal.core.chzzk.adapter.out.core.ChzzkRestExecutor;
import com.chzzkzzal.core.chzzk.application.port.out.ChzzkDevelopersApiPort;
import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChzzkDevelopersApiRestAdapter implements ChzzkDevelopersApiPort {

	private static final String TOKEN_URL = "https://openapi.chzzk.naver.com/auth/v1/token";
	private static final String USER_URL = "https://openapi.chzzk.naver.com/open/v1/users/me";
	private static final String CHANNEL_URL = "https://openapi.chzzk.naver.com/open/v1/channels";

	private final ChzzkHttpRequestFactory factory;
	private final ChzzkRestExecutor exec;

	@Override
	public ChzzkTokenResponse requestAccessToken(String code, String state) {
		var req = factory.base(POST, TOKEN_URL)
			.addBody(GRANT_TYPE.getDisplayName(), AUTHORIZATION_CODE.getDisplayName())
			.addBody(CODE.getDisplayName(), code)
			.addBody(STATE.getDisplayName(), state);
		return exec.exchange(req, ChzzkTokenResponse.class);
	}

	@Override
	public ChzzkUserResponse requestUserChannelInfo(String token) {
		var req = factory.base(GET, USER_URL)
			.addHeader(AUTHORIZATION.getDisplayName(),
				BEARER_SPACEBAR.getDisplayName() + token);
		return exec.exchange(req, ChzzkUserResponse.class);
	}

	@Override
	public ChzzkChannelInfoResponse requestChannelInfo(String[] ids) {
		String joined = String.join(",", ids);

		String url = UriComponentsBuilder.fromHttpUrl(CHANNEL_URL)
			.queryParam(CHANNEL_IDS.getDisplayName(), joined)
			.toUriString();

		return exec.exchange(factory.base(GET, url), ChzzkChannelInfoResponse.class);
	}
}
