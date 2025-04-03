package com.chzzkzzal.zzal.domain.model.factory;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.entity.Zzal;
import com.chzzkzzal.zzal.domain.model.entity.ZzalMetaInfo;

// ZzalFactory.java
public interface ZzalFactory {
    boolean supports(ZzalMetaInfo metadata);
    Zzal createZzal(Member member, ZzalMetaInfo metadata, String title,String url);
}
