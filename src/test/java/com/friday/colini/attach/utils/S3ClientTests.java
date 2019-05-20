package com.friday.colini.attach.utils;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.friday.colini.attach.properties.AwsProperties;
import com.friday.colini.attach.properties.FileProperties;
import com.friday.colini.attach.utils.random.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3ClientTests {
    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private AwsProperties awsProperties;

    @Autowired
    private S3Client s3Client;

    private String fileName;
    private String contentType;
    private byte[] fileContent;

    //
    //
    //

    @Before
    public void setUp() {
        fileName = randomUtils.getSecureString(10);
        contentType = "text/plain";
        fileContent = "HeLlO WoRlD!".getBytes();
    }

    @Test
    public void uploadAndDownloadTest() throws IOException {
        final var uploadPath = getUploadPath();
        final var uploadFile = new MockMultipartFile(fileName, fileName, contentType, fileContent);

        upload(
                uploadPath,
                uploadFile
        );
        final var downloadFile = download(uploadPath);

        Assertions.assertThat(uploadFile.getBytes()).isEqualTo(downloadFile);
    }

    //
    //
    //

    private void upload(
            final String uploadPath,
            final MultipartFile file
    ) throws IOException {
        s3Client.upload(
                awsProperties.getS3().getBucketName(),
                uploadPath,
                file,
                CannedAccessControlList.Private
        );
    }

    private byte[] download(final String uploadPath) throws IOException {
        return s3Client.download(
                awsProperties.getS3().getBucketName(),
                uploadPath
        );
    }

    private String getUploadPath() {
        return Paths.get(fileProperties.getDefaultUploadPath(), getTimebaseUUIDStrategyFilename()).toString();
    }

    private String[] getTimebaseUUIDStrategyFilename() {
        final var filename = randomUtils.getTimebaseUUID();

        // Note: https://en.wikipedia.org/wiki/Universally_unique_identifier
        return new String[]{
                filename.substring(0, 2),
                filename.substring(2, 4),
                filename.substring(4, 6),
                filename.substring(6, 8),
                filename.substring(8),
        };
    }
}
