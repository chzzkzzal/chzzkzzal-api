package com.chzzkzzal.zzal.business.domain.zzal.zzal.entity;

import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.Gif;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("GIF")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public final class GifZzal extends Zzal implements Uploadable, Bookmarkable, ViewAddable {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Embedded
	private Gif metaInfo;

	private String url;

	public static GifZzal create(String channelId, Member member, Gif gifInfo, String title, String url) {
		GifZzal gifZzal = new GifZzal();
		gifZzal.channelId = channelId;
		gifZzal.member = member;
		gifZzal.metaInfo = gifInfo;
		gifZzal.title = title;
		gifZzal.url = url;
		return gifZzal;
	}

	public static GifZzal create(Member member, MediaMeta metadata, String title, String url) {
		if (!(metadata instanceof Gif)) {
			throw new IllegalArgumentException("metadata는 GifInfo의 인스턴스여야 합니다.");
		}
		return create(member, metadata, title, url);
	}

}
