package com.friday.colini.attach.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.friday.colini.attach.exception.PlatformException;
import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.properties.AwsProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@Component
public class S3Client {
    private final AmazonS3 s3;

    @Getter
    private final boolean disable;

    @Autowired
    private S3Client(final AwsProperties awsProperties) {
        disable = awsProperties.getS3().isDisable();

        if (disable) {
            log.info("Amazon s3 client disable");
            s3 = null;
            return;
        }

        final var credentials = awsCredentials(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey()
        );
        final var credentialsProvider = credentialsProvider(credentials);
        final var region = Regions.fromName(awsProperties.getRegion());

        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }

    //
    //
    //

    public PutObjectResult upload(
            final String bucketName,
            final String uploadPath,
            final MultipartFile file,
            final CannedAccessControlList acl
    ) throws IOException {
        final var metadata = metadata(file);

        return upload(
                bucketName,
                uploadPath,
                file.getBytes(),
                metadata,
                acl
        );
    }

    public PutObjectResult upload(
            final String bucketName,
            final String uploadPath,
            final byte[] content,
            final ObjectMetadata metadata,
            final CannedAccessControlList acl
    ) {
        return upload(
                bucketName,
                uploadPath,
                new ByteArrayInputStream(content),
                metadata,
                acl
        );
    }

    public PutObjectResult upload(
            final String bucketName,
            final String uploadPath,
            final ByteArrayInputStream content,
            final ObjectMetadata metadata,
            final CannedAccessControlList acl
    ) {
        if (disable) {
            throw PlatformException.builder().status(PlatformStatus.NOT_SUPPORT).build();
        }

        final var request = uploadRequest(
                bucketName,
                uploadPath,
                content,
                metadata,
                acl
        );

        return s3.putObject(request);
    }

    public byte[] download(
            final String bucketName,
            final String uploadPath
    ) throws IOException {
        if (disable) {
            throw PlatformException.builder().status(PlatformStatus.NOT_SUPPORT).build();
        }

        final var request = downloadRequest(
                bucketName,
                uploadPath
        );
        final var downloadObject = s3.getObject(request);
        final var content = downloadObject.getObjectContent();

        return IOUtils.toByteArray(content);
    }

    //
    //
    //

    private AWSStaticCredentialsProvider credentialsProvider(final AWSCredentials credentials) {
        return new AWSStaticCredentialsProvider(credentials);
    }

    private AWSCredentials awsCredentials(
            final String accessKey,
            final String secretKey
    ) {
        return new BasicAWSCredentials(
                accessKey,
                secretKey
        );
    }

    private ObjectMetadata metadata(final MultipartFile file) {
        final var metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        return metadata;
    }

    private PutObjectRequest uploadRequest(
            final String bucketName,
            final String uploadPath,
            final ByteArrayInputStream content,
            final ObjectMetadata metadata,
            final CannedAccessControlList acl
    ) {
        return new PutObjectRequest(
                bucketName,
                uploadPath,
                content,
                metadata
        ).withCannedAcl(acl);
    }

    private GetObjectRequest downloadRequest(
            final String bucketName,
            final String downloadPath
    ) {
        return new GetObjectRequest(
                bucketName,
                downloadPath
        );
    }
}
