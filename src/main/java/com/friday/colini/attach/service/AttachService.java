package com.friday.colini.attach.service;

import com.friday.colini.attach.entity.AttachFile;
import com.friday.colini.attach.entity.AttachRequest;
import com.friday.colini.attach.exception.PlatformException;
import com.friday.colini.attach.exception.PlatformStatus;
import com.friday.colini.attach.repository.AttachDownloadRepository;
import com.friday.colini.attach.repository.AttachFileRepository;
import com.friday.colini.attach.repository.AttachRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AttachService {
    private final AttachFileRepository attachFileRepository;
    private final AttachRequestRepository attachRequestRepository;
    private final AttachDownloadRepository attachDownloadRepository;

    //
    //
    //

    @Autowired
    private AttachService(
            final AttachFileRepository attachFileRepository,
            final AttachRequestRepository attachRequestRepository,
            final AttachDownloadRepository attachDownloadRepository
    ) {
        this.attachFileRepository = attachFileRepository;
        this.attachRequestRepository = attachRequestRepository;
        this.attachDownloadRepository = attachDownloadRepository;
    }

    //
    //
    //

    public AttachRequest saveAttachRequest(final List<MultipartFile> files) {
        final var attachRequest = new AttachRequest(files);

        return attachRequestRepository.save(attachRequest);
    }

    public AttachRequest findAttachRequest(final long attachRequestId) {
        return attachRequestRepository.findById(attachRequestId).orElseThrow(
                () -> PlatformException.builder().status(PlatformStatus.BAD_REQUEST).build()
        );
    }

    public AttachFile findAttachFile(
            final long attachRequestId,
            final long attachFileId
    ) {
        final var attachFile = attachFileRepository.findById(
                attachFileId
        ).orElseThrow(
                () -> PlatformException.builder().status(PlatformStatus.BAD_REQUEST).build()
        );

        if (attachFile.isNotEqualsAttachRequestId(attachRequestId)) {
            throw PlatformException.builder().status(PlatformStatus.BAD_REQUEST).build();
        }

        // Note: No lock
        attachDownloadRepository.increaseDownloadCount(attachFileId);

        return attachFile;
    }
}
