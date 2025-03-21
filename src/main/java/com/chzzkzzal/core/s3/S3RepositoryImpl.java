package com.chzzkzzal.core.s3;

import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class S3RepositoryImpl implements S3Repository {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void putObject(String key, InputStream inputStream, ObjectMetadata metadata) {
        amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public URL getFileUrl(String key) {
        return amazonS3.getUrl(bucket, key);
    }

    @Override
    public void deleteObject(String key) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    @Override
    public ObjectMetadata getObjectMetadata(String key) {
        return amazonS3.getObjectMetadata(bucket, key);
    }
}
