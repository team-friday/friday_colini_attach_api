package com.friday.colini.attach.controller;

import com.friday.colini.attach.entity.AttachRequest;
import com.friday.colini.attach.model.Header;
import com.friday.colini.attach.model.Response;
import com.friday.colini.attach.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(
        path = "/attach/attachments",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
class AttachController {
    private final AttachService attachService;

    //
    //
    //

    @Autowired
    private AttachController(final AttachService attachService) {
        this.attachService = attachService;
    }

    //
    //
    //

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<AttachRequest> saveAttachRequest(final List<MultipartFile> files) {
        return Response.ok(attachService.saveAttachRequest(files));
    }

    @GetMapping(path = "/{attachRequestId}")
    private ResponseEntity<AttachRequest> findAttachRequest(@PathVariable final long attachRequestId) {
        return Response.ok(attachService.findAttachRequest(attachRequestId));
    }

    @GetMapping(
            path = "/{attachRequestId}/files/{attachFileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    private ResponseEntity<AbstractResource> downloadAttachFile(
            @PathVariable final long attachRequestId,
            @PathVariable final long attachFileId
    ) {
        final var attachFile = attachService.findAttachFile(
                attachRequestId,
                attachFileId
        );

        final var header = new Header();
        header.setContentType(attachFile.getContentType());
        header.setAttachmentFilename(attachFile.getOriginalName());

        return Response.ok().headers(header).body(attachFile.getFile());
    }
}
