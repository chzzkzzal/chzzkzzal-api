package com.chzzkzzal.zzal.domain.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.dto.ExampleData;
import com.chzzkzzal.zzal.domain.dto.ExampleResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalViewLogServiceImpl {
	private final LoadZzalPort loadZzalPort;

	public ExampleResult processExample(ExampleData exampleData) {
		return new ExampleResult(exampleData.value());
	}
}
