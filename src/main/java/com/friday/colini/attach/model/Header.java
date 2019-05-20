package com.friday.colini.attach.model;

import org.springframework.http.HttpHeaders;

public class Header extends HttpHeaders {
    public void setContentType(final String contentType) {
        set(Header.CONTENT_TYPE, contentType);
    }

    public void setContentDisposition(final String contentDisposition) {
        set(Header.CONTENT_DISPOSITION, contentDisposition);
    }

    public void setAttachmentFilename(final String filename) {
        setContentDisposition("attachment; filename=" + filename);
    }
}
