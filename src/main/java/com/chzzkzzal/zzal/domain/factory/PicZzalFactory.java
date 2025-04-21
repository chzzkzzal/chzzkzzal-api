package com.chzzkzzal.zzal.domain.factory;// PicZzalFactory.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.metadata.vo.PicInfo;
import com.chzzkzzal.zzal.domain.zzal.PicZzal;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;

@Component
public class PicZzalFactory implements ZzalFactory {
	@Override
	public boolean supports(ZzalMetaInfo metadata) {
		return metadata instanceof PicInfo;
	}

	@Override
	public Zzal createZzal(String channelId, Member member, ZzalMetaInfo metadata, String title, String url) {
		return PicZzal.create(channelId, member, (PicInfo)metadata, title, url);
	}
}
