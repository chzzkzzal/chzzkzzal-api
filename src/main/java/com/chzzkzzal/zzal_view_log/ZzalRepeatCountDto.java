package com.chzzkzzal.zzal_view_log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ZzalRepeatCountDto {
    private Long zzalId;         // 게시글 ID
    private Long repeatedViews;  // 중복(반복) 방문 수
}
