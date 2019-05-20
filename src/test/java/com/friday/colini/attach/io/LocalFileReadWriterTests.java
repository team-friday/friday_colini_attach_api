package com.friday.colini.attach.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalFileReadWriterTests {
    private static final String contentType = "text/plain";
    private static final byte[] content = "test".getBytes();

    @Autowired
    private LocalFileReadWriter readWriter;

    //
    //
    //

    @Test
    public void writeTest() throws IOException {
        // Given
        final var file = getTestFile();

        // When / Then
        readWriter.write(file);
    }

    @Test
    public void readTest() throws IOException {
        // Given
        final var uploadPath = readWriter.write(getTestFile());

        // When
        final var downloadFile = readWriter.read(uploadPath);

        // Then
        assertThat(downloadFile).isEqualTo(content);
    }

    //
    //
    //

    private MockMultipartFile getTestFile() {
        return new MockMultipartFile(
                "files",
                "testfile",
                contentType,
                content
        );
    }
}
