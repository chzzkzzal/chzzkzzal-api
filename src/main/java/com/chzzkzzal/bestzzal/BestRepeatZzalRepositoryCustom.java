package com.chzzkzzal.bestzzal;

import java.util.List;

public interface BestRepeatZzalRepositoryCustom {
    List<Long> findTop5ZzalIdsByStreamerIdUsingQuerydsl(Long streamerId);
}
