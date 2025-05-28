package com.chzzkzzal.zzal.business.domain.hit;

import static com.chzzkzzal.zzal.infrastructure.hit.UserAgentAnalyzer.*;

import java.time.LocalDate;

import com.chzzkzzal.zzal.business.application.dto.ClientInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "zzal_hits",
	indexes = @Index(columnList = "zzalId", name = "zzaliId_idx"
	)
)

public class ZzalHit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long zzalId;

	@Column(nullable = false, unique = true)
	private String uniqueKey;

	@Column(nullable = false)
	private String ipAddress;

	@Column(nullable = false, length = 500)
	private String userAgent;

	@Column
	@Enumerated(EnumType.STRING)
	private BrowserType browserType;

	@Column
	@Enumerated(EnumType.STRING)
	private DeviceType deviceType;

	@NotNull
	private LocalDate viewDate;

	@Builder
	private ZzalHit(Long zzalId, String uniqueKey, String ipAddress,
		String userAgent, BrowserType browserType,
		DeviceType deviceType, LocalDate viewDate) {

		this.zzalId = zzalId;
		this.uniqueKey = uniqueKey;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
		this.browserType = browserType;
		this.deviceType = deviceType;
		this.viewDate = viewDate;
	}

	public static ZzalHit of(Long zzalId,
		String uniqueKey,
		ClientInfo clientInfo) {

		LocalDate viewDate = LocalDate.now();

		return ZzalHit.builder()
			.zzalId(zzalId)
			.uniqueKey(uniqueKey)
			.ipAddress(clientInfo.ipAddress())
			.userAgent(clientInfo.userAgent())
			.browserType(clientInfo.browserType())
			.deviceType(clientInfo.deviceType())
			.viewDate(viewDate)
			.build();
	}

}
