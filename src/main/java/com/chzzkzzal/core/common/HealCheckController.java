package com.chzzkzzal.core.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealCheckController {
	@GetMapping
	public String check() {
		return "IS GOOD";
	}
}
