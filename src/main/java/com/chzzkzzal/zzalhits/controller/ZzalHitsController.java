package com.chzzkzzal.zzalhits.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chzzkzzal.core.error.CustomResponse;
import com.chzzkzzal.zzalhits.service.ZzalHitsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/view")
public class ZzalHitsController {
	private final ZzalHitsService zzalHitsService;

	@GetMapping("/count/{zzalId}")
	public ResponseEntity<CustomResponse<Long>> count(@PathVariable Long zzalId) {
		Long response = zzalHitsService.count(zzalId);
		return CustomResponse.okResponseEntity(response);
	}
}
