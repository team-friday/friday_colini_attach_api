package com.friday.colini.attach.controller;

import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DefaultController {
    @RequestMapping("/health")
    ResponseEntity healthCheck() {
        return Response.ok().build();
    }

    @RequestMapping
    ResponseEntity notFound() {
        return PlatformStatus.NOT_FOUND.toResponse();
    }
}
