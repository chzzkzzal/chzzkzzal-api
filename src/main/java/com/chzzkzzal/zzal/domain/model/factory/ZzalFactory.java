package com.chzzkzzal.zzal.domain.model.factory;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;

// ZzalFactory.java
public interface ZzalFactory {
	boolean supports(ZzalMetaInfo metadata);

	Zzal createZzal(Long streamerId, Member member, ZzalMetaInfo metadata, String title, String url);
}
