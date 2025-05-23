package com.chzzkzzal.zzal.domain.model.zzal;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.model.metadata.PicInfo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("PIC")
@Entity
@NoArgsConstructor
@Getter
public class PicZzal extends Zzal {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@Embedded
	private PicInfo metaInfo;

	private String url;

	public static PicZzal create(String channelId, Member member, PicInfo picInfo, String title, String url) {
		PicZzal picZzal = new PicZzal();
		picZzal.channelId = channelId;
		picZzal.member = member;
		picZzal.metaInfo = picInfo;
		picZzal.title = title;
		picZzal.url = url;
		return picZzal;
	}

	public static PicZzal create(Member member, ZzalMetaInfo metadata, String title, String url) {
		if (!(metadata instanceof PicInfo)) {
			throw new IllegalArgumentException("metadata는 PicInfo의 인스턴스여야 합니다.");
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
