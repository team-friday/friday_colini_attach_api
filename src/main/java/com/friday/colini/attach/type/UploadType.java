package com.friday.colini.attach.type;

import com.friday.colini.attach.exception.PlatformException;
import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.io.Reader;
import com.friday.colini.attach.io.Writer;
import com.friday.colini.attach.utils.BeanUtils;
import com.friday.colini.attach.utils.FileReadWriterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public enum UploadType {
    Local,
    S3

    ;

    //
    //
    //

    public byte[] download(final String filePath) {
        try {
            return getReader().read(filePath);

        } catch (final FileNotFoundException e) {
            throw PlatformException.builder().status(PlatformStatus.FILE_NOT_FOUND).build();

        } catch (final IOException e) {
            log.error("File download fail", e);

            throw PlatformException.builder().status(PlatformStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String upload(final MultipartFile file) {
        try {
            return getWriter().write(file);

        } catch (final IOException e) {
            log.error("File upload fail", e);

            throw PlatformException.builder().build();
        }
    }

    //
    //
    //

    private Reader getReader() {
        return BeanUtils.getBean(FileReadWriterProvider.class).getReader(this);
    }

    private Writer getWriter() {
        return BeanUtils.getBean(FileReadWriterProvider.class).getWriter(this);
    }
}
