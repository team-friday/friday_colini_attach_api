package com.friday.colini.attach.io;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Writer {
    String write(final MultipartFile file) throws IOException;
}
