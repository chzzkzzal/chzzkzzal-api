//package com.chzzkzzal.core.external.chzzk.adapter.out;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Component;
//
//import com.chzzkzzal.core.external.chzzk.application.out.ChzzkApiPort;
//import com.chzzkzzal.core.external.chzzk.domain.model.ChannelId;
//import com.chzzkzzal.core.external.common.response.ChzzkChannelInfoResponse;
//import com.chzzkzzal.core.external.common.response.ChzzkTokenResponse;
//import com.chzzkzzal.core.external.common.response.ChzzkUserResponse;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class ChzzkApiRestAdapterV2 implements ChzzkApiPort {
//
//	private final WebClient chzzkClient;
//
//	@Override
//	public ChzzkTokenResponse requestAccessToken(String code, String state) {
//		return chzzkClient.post()
//			.uri("/auth/v1/token")
//			.bodyValue("grant_type=authorization_code&code=" + code + "&state=" + state)
//			.retrieve()
//			.bodyToMono(ChzzkTokenResponse.class)
//			.block();
//	}
//
//	@Override
//	public ChzzkUserResponse requestUserChannelInfo(String accessToken) {
//		return chzzkClient.get()
//			.uri("/open/v1/users/me")
//			.header("Authorization", "Bearer " + accessToken)
//			.retrieve()
//			.bodyToMono(ChzzkUserResponse.class)
//			.block();
//	}
//
//	@Override
//	public ChzzkChannelInfoResponse requestChannelInfo(List<ChannelId> ids) {
//		String joined = ids.stream().map(ChannelId::value).collect(Collectors.joining(","));
//		return chzzkClient.get()
//			.uri(uriBuilder -> uriBuilder.path("/open/v1/channels")
//				.queryParam("channelIds", joined)
//				.build())
//			.retrieve()
//			.bodyToMono(ChzzkChannelInfoResponse.class)
//			.block();
//	}
//}
