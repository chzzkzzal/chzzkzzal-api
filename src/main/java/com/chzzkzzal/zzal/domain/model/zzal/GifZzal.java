package com.chzzkzzal.zzal.domain.model.zzal;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.metadata.GifInfo;

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
public class GifZzal extends Zzal {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Embedded
	private GifInfo metaInfo;

	private String url;

	public static GifZzal create(String channelId, Member member, GifInfo gifInfo, String title, String url) {
		GifZzal gifZzal = new GifZzal();
		gifZzal.channelId = channelId;
		gifZzal.member = member;
		gifZzal.metaInfo = gifInfo;
		gifZzal.title = title; // 상위 클래스의 title 필드 설정
		gifZzal.url = url;
		return gifZzal;
	}

	public static GifZzal create(Member member, ZzalMetaInfo metadata, String title, String url) {
		if (!(metadata instanceof GifInfo)) {
			throw new IllegalArgumentException("metadata는 GifInfo의 인스턴스여야 합니다.");
		}
		return create(member, metadata, title, url);
	}

	@Override
	public Zzal upload() {
		return null;
	}

	@Override
	public void bookmark() {

	}

	@Override
	public void view() {

	}

	@Override
	public int countTotalView() {
		return 0;
	}
}
