package com.chzzkzzal.zzalhits.domain;

import static com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer.*;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
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

public class ZzalHits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long zzalId;

	@Column(nullable = false, unique = true)
	private String uniqueIdentifier;

	@Column(nullable = false)
	private String ipAddress;

	// User Agent
	@Column(nullable = false, length = 500)
	private String userAgent;

	// 브라우저 타입(크롬,사파리...)
	@Column
	@Enumerated(EnumType.STRING)
	private BrowserType browserType;

	// 디바이스 타입 (모바일/데스크톱)
	@Column
	@Enumerated(EnumType.STRING)
	private DeviceType deviceType;

	@NotNull
	private LocalDate viewDate;

	@Builder
	public ZzalHits(Long zzalId, String uniqueIdentifier, String ipAddress,
		String userAgent, BrowserType browserType,
		DeviceType deviceType, LocalDate viewDate){

		this.zzalId = zzalId;
		this.uniqueIdentifier = uniqueIdentifier;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
		this.browserType = browserType;
		this.deviceType = deviceType;
		this.viewDate = viewDate;
	}

	public static ZzalHits addFromRequest(Long zzalId, HttpServletRequest request) {
		String ipAddress = ClientIpExtractor.extractIpAddress(request);

		UserAgentInfo userAgentInfo = analyze(request);
		String userAgent = userAgentInfo.userAgent();
		BrowserType browserType = userAgentInfo.browserType();
		DeviceType deviceType = userAgentInfo.deviceType();

		String uniqueIdentifier = UniqueIdentifierGenerator.generate(ipAddress, userAgent);
		LocalDate viewDate = LocalDate.now();

		return ZzalHits.builder()
			.zzalId(zzalId)
			.uniqueIdentifier(uniqueIdentifier)
			.ipAddress(ipAddress)
			.userAgent(userAgent)
			.browserType(browserType)
			.deviceType(deviceType)
			.viewDate(viewDate)
			.build();
	}


}
