package com.chzzkzzal.core.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.client.facade.ChannelData;
import com.chzzkzzal.core.client.facade.ChzzkChannelInfoClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chzzk")
@RequiredArgsConstructor
public class ChzzkControllerV2 {

	private final ChzzkAPIService chzzkAPIService;
	private final ChzzkChannelInfoClient channelInfoClient;

	@GetMapping("/channels")
	public ChannelData getChannels(@RequestParam String[] channelIds) {
		return channelInfoClient.fetchChannelInfo(channelIds);
	}
}
