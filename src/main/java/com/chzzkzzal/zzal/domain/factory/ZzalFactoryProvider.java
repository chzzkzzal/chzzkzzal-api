package com.chzzkzzal.zzal.domain.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZzalFactoryProvider {
	private final List<ZzalFactory> factories;

	public ZzalFactory getFactory(ZzalMetaInfo metadata) {
		return factories.stream()
			.filter(f -> f.supports(metadata))
			.findFirst()
			.orElseThrow(ZzalNotFoundException::new);
	}
}
