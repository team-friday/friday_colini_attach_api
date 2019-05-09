package com.friday.colini.attach.exception;

import com.friday.colini.attach.model.Error;
import com.friday.colini.attach.model.Header;
import com.friday.colini.attach.model.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public enum PlatformStatus {
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

    ;

    private final HttpStatus httpStatus;

    public @NonNull ResponseEntity<Error> toResponse() {
        return toResponse(null);
    }

    public @NonNull ResponseEntity<Error> toResponse(@Nullable final Header header) {
        return Response.status(httpStatus).headers(header).body(toError());
    }

    private @NonNull Error toError() {
        return Error.builder().code(getErrorCode()).message(getErrorMessage()).build();
    }

    private @NonNull String getErrorCode() {
        // TODO:
        return String.format("%d", httpStatus.value());
    }

    private @NonNull String getErrorMessage() {
        // TODO:
        return name();
    }
}
