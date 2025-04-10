package com.chzzkzzal.zzal_view_log;

import static com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer;

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
@Table(name = "zzal_view_log")

public class ZzalViewLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long zzalId;
	private Long memberId;

	@Column(nullable = false)
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
	private LocalDateTime viewDateTime;

	@Builder
	public ZzalViewLog(Long zzalId, Long memberId, String uniqueIdentifier, String ipAddress, String userAgent,
		BrowserType browserType, DeviceType deviceType,
		@NotNull LocalDateTime viewDateTime) {
		this.zzalId = zzalId;
		this.memberId = memberId;
		this.uniqueIdentifier = uniqueIdentifier;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
		this.browserType = browserType;
		this.deviceType = deviceType;
		this.viewDateTime = viewDateTime;
	}

	public static ZzalViewLog addViewLog(Long zzalId, Long memberId, String uniqueIdentifier, String ipAddress, String userAgent,
		BrowserType browserType, DeviceType deviceType, LocalDateTime localDateTime){

		return ZzalViewLog.builder()
			.zzalId(zzalId)
			.memberId(memberId)
			.uniqueIdentifier(uniqueIdentifier)
			.ipAddress(ipAddress)
			.userAgent(userAgent)
			.browserType(browserType)
			.deviceType(deviceType)
			.viewDateTime(localDateTime)
			.build();
	}
}
