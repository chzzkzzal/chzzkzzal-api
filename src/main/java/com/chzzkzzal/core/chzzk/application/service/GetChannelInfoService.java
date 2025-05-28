package com.chzzkzzal.core.chzzk.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.chzzk.application.port.in.GetChannelInfoUseCase;
import com.chzzkzzal.core.chzzk.application.port.out.ChzzkDevelopersApiPort;
import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetChannelInfoService implements GetChannelInfoUseCase {
	private final ChzzkDevelopersApiPort api;

	@Override
	public ChzzkChannelInfoResponse getChannelInfoByIds(String[] ids) {
		return api.requestChannelInfo(ids);
	}
}
