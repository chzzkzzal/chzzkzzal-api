package com.chzzkzzal.core.external.chzzk.intrastructure.http.channel;

import static com.chzzkzzal.core.external.chzzk.domain.ChzzkApiFields.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.chzzkzzal.core.external.chzzk.application.port.ChzzkChannelPort;
import com.chzzkzzal.core.external.chzzk.domain.model.ChannelInfo;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequest;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkHttpRequestFactory;
import com.chzzkzzal.core.external.chzzk.intrastructure.core.ChzzkRestExecutor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChannelHttpClient implements ChzzkChannelPort {
	private static final String CHANNEL_INFO_URL = "https://openapi.chzzk.naver.com/open/v1/channels";
	private final ChzzkHttpRequestFactory factory;
	private final ChzzkRestExecutor exec;

	@Override
	public ChannelInfo findByIds(String[] ids) {
		String joinedIds = String.join(",", ids);
		String url = UriComponentsBuilder.fromHttpUrl(CHANNEL_INFO_URL)
			.queryParam(CHANNEL_IDS.getDisplayName(), joinedIds)
			.toUriString();

		ChzzkHttpRequest req = factory.base(GET, url);

		return exec.exchange(req, ChannelInfo.class);
	}
}
