package com.friday.colini.attach.entity;

import com.friday.colini.attach.repository.AttachRequestRepository;
import com.friday.colini.attach.utils.random.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachEntityTests {
    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private AttachRequestRepository attachRequestRepository;

    //
    //
    //

    @Test
    public void saveSingleFileAttachRequestTest() {
        // Given
        final var fileName = randomUtils.getSecureString(10);
        final var contentType = "text/plain";
        final var fileContent = "HeLlO WoRlD!".getBytes();

        final var files = List.<MultipartFile>of(
                new MockMultipartFile(fileName, fileName, contentType, fileContent)
        );

        final var attachRequest = new AttachRequest(files);


        // When
        attachRequestRepository.save(attachRequest);
        final var findAttachRequest = attachRequestRepository.findById(attachRequest.getId()).orElseThrow();


        // Then
        Assertions.assertThat(findAttachRequest.getId()).isNotEqualTo(0);
        Assertions.assertThat(findAttachRequest.getAttachFiles().size()).isEqualTo(1);

        final var attachFile = findAttachRequest.getAttachFiles().get(0);

        Assertions.assertThat(attachFile).isNotNull();
        Assertions.assertThat(attachFile.getContentType()).isEqualTo(contentType);
        Assertions.assertThat(attachFile.getOriginalName()).isEqualTo(fileName);
        Assertions.assertThat(attachFile.getCreatedAt()).isNotNull();
        Assertions.assertThat(attachFile.getLastDownloadAt()).isNotNull();
    }

    @Test
    public void saveMultipleFileAttachRequestTest() {
        // Given
        final var fileCount = 199;
        final var contentType = "text/plain";
        final var fileContent = "HeLlO WoRlD!";

        final var fileInfos = IntStream.rangeClosed(1, fileCount)
                .mapToObj(
                        i -> new Object[]{
                                randomUtils.getSecureString(10),
                                contentType,
                                (fileContent + "_" + i).getBytes()
                        }
                )
                .collect(Collectors.toList());


        final var files = fileInfos.stream()
                .map(
                        fileInfo -> new MockMultipartFile(
                                (String) fileInfo[0],
                                (String) fileInfo[0],
                                (String) fileInfo[1],
                                (byte[]) fileInfo[2]
                        )
                ).collect(Collectors.<MultipartFile>toList());

        final var attachRequest = new AttachRequest(files);

        // When
        attachRequestRepository.save(attachRequest);
        final var findAttachRequest = attachRequestRepository.findById(attachRequest.getId()).orElseThrow();

        // Then
        Assertions.assertThat(findAttachRequest.getId()).isNotEqualTo(0);
        Assertions.assertThat(findAttachRequest.getAttachFiles().size()).isEqualTo(fileCount);

        for (var i = 0; i < fileCount; i++) {
            final var attachFile = findAttachRequest.getAttachFiles().get(i);
            final var fileInfo = fileInfos.get(i);

            Assertions.assertThat(attachFile).isNotNull();
            Assertions.assertThat(attachFile.getContentType()).isEqualTo(fileInfo[1]);
            Assertions.assertThat(attachFile.getOriginalName()).isEqualTo(fileInfo[0]);
            Assertions.assertThat(attachFile.getCreatedAt()).isNotNull();
            Assertions.assertThat(attachFile.getLastDownloadAt()).isNotNull();
        }
    }
}
