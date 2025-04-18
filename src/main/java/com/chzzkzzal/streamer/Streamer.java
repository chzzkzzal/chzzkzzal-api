package com.chzzkzzal.streamer;

import com.chzzkzzal.core.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Streamer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "streamer_id")
	private Long id;
	private String channelId;

	private String channelName;

	private String channelImageUrl;

	private int followerCount;

	@Builder
	public Streamer(String channelId, String channelName, String channelImageUrl, int followerCount) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelImageUrl = channelImageUrl;
		this.followerCount = followerCount;
	}

	public static Streamer register(String channelId, String channelName, String channelImageUrl, int followerCount) {
		return Streamer.builder()
			.channelId(channelId)
			.channelName(channelName)
			.channelImageUrl(channelImageUrl)
			.followerCount(followerCount)
			.build();
	}

}
