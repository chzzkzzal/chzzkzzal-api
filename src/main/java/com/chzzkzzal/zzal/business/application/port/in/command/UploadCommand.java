package com.chzzkzzal.zzal.business.application.port.in.command;

import java.io.InputStream;

public record UploadCommand(
	String channelId,
	String title,
	Long memberId,
	String name,
	String originalFilename,
	String contentType,
	long size,
	byte[] bytes,
	InputStream inputStream

) {
}
