package com.friday.colini.attach.io;

import com.friday.colini.attach.properties.FileProperties;
import com.friday.colini.attach.utils.random.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileReadWriter extends FileReadWriter {
    @Autowired
    private LocalFileReadWriter(
            final RandomUtils randomUtils,
            final FileProperties fileProperties
    ) {
        super(
                randomUtils,
                fileProperties.getDefaultUploadPath()
        );
    }

    //
    //
    //

    @Override
    public byte[] read(final String filePath) throws IOException {
        final var path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }

        return Files.readAllBytes(path);
    }

    @Override
    public String write(final MultipartFile file) throws IOException {
        final var path = getUploadPath();

        checkDirectory(path.getParent());

        return Files.write(path, file.getBytes()).toString();
    }

    //
    //
    //

    private void checkDirectory(final Path path) throws IOException {
        if (Files.exists(path)) {
            return;
        }

        createDirectory(path);
    }

    private void createDirectory(final Path path) throws IOException {
        checkDirectory(path.getParent());

        try {
            Files.createDirectory(path);
        } catch (final FileAlreadyExistsException ignore) { }
    }
}
