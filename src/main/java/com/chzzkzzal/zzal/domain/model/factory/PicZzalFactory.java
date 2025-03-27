package com.chzzkzzal.zzal.domain.model.factory;// PicZzalFactory.java
import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.zzal.PicZzal;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal.domain.model.zzal.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.metadata.PicInfo;

@Component
public class PicZzalFactory implements ZzalFactory {
    @Override
    public boolean supports(ZzalMetaInfo metadata) {
         return metadata instanceof PicInfo;
    }
    
    @Override
    public Zzal createZzal(Member member, ZzalMetaInfo metadata, String title,String url) {
         return PicZzal.create(member, (PicInfo) metadata, title,url);
    }
}
