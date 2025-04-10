package com.chzzkzzal.zzalhits.controller;

public record AddZzalHitsRequest(
	Long zzalId,
	String ipAddress,
	String userAgent

) {
}
