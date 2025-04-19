package com.chzzkzzal.zzal_view_log;

import java.util.List;

public interface ZzalViewLogRepositoryCustom {
    /**
     * "또 보고싶어지는 게시글" TOP5
     * (uniqueIdentifier 기준 중복 방문이 많은 순서)
     */
    List<ZzalRepeatCountDto> findTop5ByRepeatViews();
}
