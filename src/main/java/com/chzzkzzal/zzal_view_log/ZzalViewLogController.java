package com.chzzkzzal.zzal_view_log;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class ZzalViewLogController {
	private final ZzalViewLogService zzalViewLogService;

	@GetMapping("/top5")
	public List<ZzalRepeatCountDto> getTop5(){
		return  zzalViewLogService.getTop5RepeatedZzal();
	}
}
