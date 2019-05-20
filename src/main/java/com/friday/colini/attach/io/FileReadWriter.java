package com.friday.colini.attach.io;

import com.friday.colini.attach.utils.random.RandomUtils;
import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
abstract class FileReadWriter implements ReadWriter {
    private final RandomUtils randomUtils;
    private final String uploadPath;

    //
    //
    //

    Path getUploadPath() {
        return Paths.get(uploadPath, getTimebaseUUIDStrategyFilename());
    }

    //
    //
    //

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
