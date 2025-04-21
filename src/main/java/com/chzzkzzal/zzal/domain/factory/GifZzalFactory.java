package com.chzzkzzal.zzal.domain.factory;// GifZzalFactory.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.metadata.vo.GifInfo;
import com.chzzkzzal.zzal.domain.zzal.GifZzal;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;

@Component
public class GifZzalFactory implements ZzalFactory {
	@Override
	public boolean supports(ZzalMetaInfo metadata) {
		return metadata instanceof GifInfo;
	}

	@Override
	public Zzal createZzal(String channelId, Member member, ZzalMetaInfo metadata, String title, String url) {
		return GifZzal.create(channelId, member, (GifInfo)metadata, title, url);
	}
}
