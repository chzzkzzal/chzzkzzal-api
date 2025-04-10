package com.chzzkzzal.myviewhistory;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ZzalViewHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long streamerId;

	private Long zzalId;

	private Long memberId;

	@NotNull
	private LocalDateTime viewDateTime;

	@Builder
	public ZzalViewHistory(Long zzalId, Long streamerId, Long memberId,LocalDateTime viewDateTime) {
		this.zzalId = zzalId;
		this.streamerId = streamerId;
		this.memberId = memberId;
		this.viewDateTime = viewDateTime;
	}

	public static ZzalViewHistory addHistory(Long zzalId, Long memberId){
		LocalDateTime now = LocalDateTime.now();

		return ZzalViewHistory.builder()
			.zzalId(zzalId)
			.memberId(memberId)
			.viewDateTime(now)
			.build();
	}
}
