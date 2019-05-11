package com.friday.colini.attach.exception;

import com.friday.colini.attach.model.Error;
import com.friday.colini.attach.model.Header;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

@Builder
public class PlatformException extends RuntimeException {
    @Builder.Default
    private PlatformStatus status = PlatformStatus.INTERNAL_SERVER_ERROR;
    private Header header;

    //
    //
    //

    @NonNull ResponseEntity<Error> toResponse() {
        return status.toResponse(header);
    }

    @Override
    public String getMessage() {
        return status.toString();
    }
}
