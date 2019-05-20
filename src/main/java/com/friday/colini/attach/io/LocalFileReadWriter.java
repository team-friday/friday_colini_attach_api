package com.friday.colini.attach.io;

import com.friday.colini.attach.exception.PlatformException;
import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.properties.FileProperties;
import com.friday.colini.attach.utils.random.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public byte[] read(final String filePath) {
        throw PlatformException.builder().status(PlatformStatus.NOT_SUPPORT).build();
    }

    @Override
    public String write(final MultipartFile file) {
        throw PlatformException.builder().status(PlatformStatus.NOT_SUPPORT).build();
    }
}
