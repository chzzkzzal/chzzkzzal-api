package com.chzzkzzal.core.auth.application.command;

public record SignInCommand(
	String channelId,
	String channelName
) {
}
