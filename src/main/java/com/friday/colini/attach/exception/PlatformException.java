package com.friday.colini.attach.exception;

import com.friday.colini.attach.model.Error;
import com.friday.colini.attach.model.Header;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

@Builder
class PlatformException extends Exception {
    @Builder.Default
    private PlatformStatus status = PlatformStatus.INTERNAL_SERVER_ERROR;
    private Header header;

    public @NonNull ResponseEntity<Error> toResponse() {
        return status.toResponse(header);
    }
}
