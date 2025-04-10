package com.chzzkzzal.bestzzal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BestRepeatZzal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long streamerId;

	private Long zzalId;

	@NotNull
	private LocalDateTime createdAt;

	@Builder
	public BestRepeatZzal(Long streamerId, Long zzalId, LocalDateTime createdAt) {
		this.streamerId = streamerId;
		this.zzalId = zzalId;
		this.createdAt = createdAt;
	}


	public static BestRepeatZzal addBestRepeatZzal(Long streamerId, Long zzalId){
		LocalDateTime now = LocalDateTime.now();

		return BestRepeatZzal.builder()
			.streamerId(streamerId)
			.zzalId(zzalId)
			.createdAt(now)
			.build();
	}
}
