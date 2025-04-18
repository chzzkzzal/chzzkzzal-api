package com.chzzkzzal.core.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.chzzkzzal.core.common.properties.S3Properties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AWSConfig {
	private final S3Properties s3Properties;

	@Bean
	public AmazonS3Client amazonS3Client() {
		String accessKey = s3Properties.accessKey();
		String secretKey = s3Properties.secretKey();
		String region = s3Properties.region();

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
			.build();
	}
}
