package com.friday.colini.attach.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public class Response<T> extends ResponseEntity<T> {
    public Response(@NonNull final HttpStatus status) {
        super(status);
    }
}
