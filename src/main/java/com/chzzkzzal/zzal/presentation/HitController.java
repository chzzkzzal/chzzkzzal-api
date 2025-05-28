package com.chzzkzzal.zzal.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.zzal.business.application.port.in.GetHitCountUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/view")
public class HitController {
	private final GetHitCountUseCase getHitCountUseCase;

	@GetMapping("/count/{zzalId}")
	public ResponseEntity<CustomResponse<Long>> count(@PathVariable Long zzalId) {
		Long response = getHitCountUseCase.count(zzalId);
		return CustomResponse.okResponseEntity(response);
	}
}
