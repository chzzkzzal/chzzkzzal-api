package com.chzzkzzal.member.business.application.port.out;

import com.chzzkzzal.common.annotation.PublicApi;
import com.chzzkzzal.member.business.application.query.MemberInfo;

@PublicApi("External use to register member if not exists.")
public interface SaveMemberCommand {
	MemberInfo saveIfNotExist(String externalId, String channelName);

}
