package com.chzzkzzal.zzal.business.application.port.in.query;

public record ExtractMetadataQuery(
	byte[] bytes,
	String originalFilename,
	String contentType
) {
}
