package com.friday.colini.attach.exception;

import com.amazonaws.AmazonServiceException;
import com.friday.colini.attach.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<Error> platformException(final PlatformException e) {
        return e.toResponse();
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<Error> amazonServiceException(final AmazonServiceException e) {
        log.error("Amazon service exception", e);

        return PlatformStatus.INTERNAL_SERVER_ERROR.toResponse();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Error> maxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        return PlatformStatus.UPLOAD_MAX_SIZE_EXCEED.toResponse();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> uncaughtException(final Exception e) {
        log.error("Uncaught exception", e);

        return PlatformStatus.INTERNAL_SERVER_ERROR.toResponse();
    }
}