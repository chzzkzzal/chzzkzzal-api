package com.chzzkzzal.core.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record S3Properties(
	@Value("${cloud.aws.s3.bucket}") String bucket,
	@Value("${cloud.aws.credentials.accessKey}") String accessKey,
	@Value("${cloud.aws.credentials.secretKey}") String secretKey,
	@Value("${cloud.aws.region.static}") String region
) {
}





