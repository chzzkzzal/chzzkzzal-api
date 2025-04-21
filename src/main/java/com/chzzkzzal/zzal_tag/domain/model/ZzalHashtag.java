package com.chzzkzzal.zzal_tag.domain.model;

import com.chzzkzzal.zzal.domain.zzal.Zzal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zzal_hashtag",
	indexes = {
		@Index(name = "idx_zzal_id", columnList = "zzal_id"),
		@Index(name = "idx_hashtag_id", columnList = "hashtag_id")
	})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ZzalHashtag {

	@EmbeddedId
	private ZzalHashtagId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("zzalId")
	// @BatchSize(size = 100)
	private Zzal zzal;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("hashtagId")
	// @BatchSize(size = 100)
	private Hashtag hashtag;

	public ZzalHashtag(Zzal zzal, Hashtag hashtag) {
		this.zzal = zzal;
		this.hashtag = hashtag;
		this.id = new ZzalHashtagId(zzal.getId(), hashtag.getId());
	}
}
