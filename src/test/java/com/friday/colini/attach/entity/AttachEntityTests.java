package com.friday.colini.attach.entity;

import com.friday.colini.attach.repository.AttachRequestRepository;
import com.friday.colini.attach.utils.RandomUtils;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachEntityTests {
    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private AttachRequestRepository attachRequestRepository;

    @Test
    public void saveAttachRequestTest() {
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
        Assertions.assertThat(attachRequest.getId()).isNotEqualTo(0);
        Assertions.assertThat(attachRequest.getAttachFiles().size()).isEqualTo(1);

        final var attachFile = findAttachRequest.getAttachFiles().get(0);

        Assertions.assertThat(attachFile).isNotNull();
        Assertions.assertThat(attachFile.getContentType()).isEqualTo(contentType);
        Assertions.assertThat(attachFile.getOriginalName()).isEqualTo(fileName);
        Assertions.assertThat(attachFile.getCreatedAt()).isNotNull();
        Assertions.assertThat(attachFile.getLastDownloadAt()).isNotNull();
    }
}
