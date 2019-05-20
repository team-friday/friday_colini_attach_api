package com.friday.colini.attach.exception;

import com.friday.colini.attach.model.Error;
import com.friday.colini.attach.model.Header;
import com.friday.colini.attach.model.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public enum PlatformStatus {
    // Global
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_SUPPORT(HttpStatus.INTERNAL_SERVER_ERROR),

    // Attach
    UNAVAILABLE_FILE(HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND),

    ;

    //
    //
    //

    private final HttpStatus httpStatus;

    public ResponseEntity<Error> toResponse() {
        return toResponse(null);
    }

    public ResponseEntity<Error> toResponse(@Nullable final Header header) {
        return Response.status(httpStatus).headers(header).body(toError());
    }

    //
    //
    //

    private Error toError() {
        return Error.builder().code(getErrorCode()).message(getErrorMessage()).build();
    }

    private String getErrorCode() {
        // TODO: Extends error code
        return String.format("%d", httpStatus.value());
    }

    private String getErrorMessage() {
        // TODO: Extends error message
        return name();
    }
}
