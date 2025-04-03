package com.chzzkzzal.zzal.domain.model.factory;// GifZzalFactory.java
import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.entity.GifZzal;
import com.chzzkzzal.zzal.domain.model.entity.Zzal;
import com.chzzkzzal.zzal.domain.model.entity.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.metadata.GifInfo;

@Component
public class GifZzalFactory implements ZzalFactory {
    @Override
    public boolean supports(ZzalMetaInfo metadata) {
         return metadata instanceof GifInfo;
    }
    
    @Override
    public Zzal createZzal(Member member, ZzalMetaInfo metadata, String title,String url) {
         return GifZzal.create(member, (GifInfo) metadata, title,url);
    }
}
