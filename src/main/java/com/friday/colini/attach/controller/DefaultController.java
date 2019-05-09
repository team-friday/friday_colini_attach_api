package com.friday.colini.attach.controller;

import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping("/health")
    public ResponseEntity healthCheck() {
        return Response.ok().build();
    }

    @RequestMapping
    public ResponseEntity notFound() {
        return PlatformStatus.NOT_FOUND.toResponse();
    }
}
