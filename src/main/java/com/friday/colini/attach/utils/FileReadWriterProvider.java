package com.friday.colini.attach.utils;

import com.friday.colini.attach.io.*;
import com.friday.colini.attach.type.UploadType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileReadWriterProvider {
    private final LocalFileReadWriter localFileReadWriter;
    private final S3FileReadWriter s3FileReadWriter;

    //
    //
    //

    @Autowired
    private FileReadWriterProvider(
            final LocalFileReadWriter localFileReadWriter,
            final S3FileReadWriter s3FileReadWriter
    ) {
        this.localFileReadWriter = localFileReadWriter;
        this.s3FileReadWriter = s3FileReadWriter;
    }

    //
    //
    //

    public Reader getReader(final UploadType uploadType) {
        return getReadWriter(uploadType);
    }

    public Writer getWriter(final UploadType uploadType) {
        return getReadWriter(uploadType);
    }

    //
    //
    //

    private ReadWriter getReadWriter(final UploadType uploadType) {
        switch (uploadType) {
            case Local:
                return localFileReadWriter;
            case S3:
                return s3FileReadWriter;
        }

        throw new IllegalArgumentException("Not support upload type");
    }
}
