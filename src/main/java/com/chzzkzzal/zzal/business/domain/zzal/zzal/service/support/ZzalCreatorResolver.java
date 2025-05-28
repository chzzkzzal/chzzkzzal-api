package com.chzzkzzal.zzal.business.domain.zzal.zzal.service.support;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZzalCreatorResolver {
	private final List<ZzalCreator> factories;

	public ZzalCreator getFactory(MediaMeta metadata) {
		return factories.stream()
			.filter(f -> f.supports(metadata))
			.findFirst()
			.orElseThrow(ZzalNotFoundException::new);
	}
}
