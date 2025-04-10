package com.chzzkzzal.core.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chzzk")
@RequiredArgsConstructor
public class ChzzkControllerV2 {

    private final ChzzkAPIService chzzkAPIService;

    
    @GetMapping("/channels")
    public String getChannels(@RequestParam List<String> channelIds) {
        return chzzkAPIService.getChannelInfos(channelIds);
    }
}
