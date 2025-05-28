package com.chzzkzzal.zzal.business.domain.zzal.zzal.entity;

import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.Pic;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("PIC")
@Entity
@NoArgsConstructor
@Getter
public final class PicZzal extends Zzal implements Uploadable, Bookmarkable, ViewAddable {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Embedded
	private Pic metaInfo;

	@NotEmpty(message = "파일주소는 필수 입력 항목입니다.")
	private String url;

	public static PicZzal create(String channelId, Member member, Pic picInfo, String title, String url) {
		PicZzal picZzal = new PicZzal();
		picZzal.channelId = channelId;
		picZzal.member = member;
		picZzal.metaInfo = picInfo;
		picZzal.title = title;
		picZzal.url = url;
		return picZzal;
	}

	public static PicZzal create(Member member, MediaMeta metadata, String title, String url) {
		if (!(metadata instanceof Pic)) {
			throw new IllegalArgumentException("metadata는 PicInfo의 인스턴스여야 합니다.");
		}
		return create(member, metadata, title, url);
	}

}
