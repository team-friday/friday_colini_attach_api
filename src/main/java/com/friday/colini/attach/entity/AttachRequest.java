package com.friday.colini.attach.entity;

import com.friday.colini.attach.exception.PlatformException;
import com.friday.colini.attach.exception.PlatformStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // Using JPA
@Table
@Entity
@DynamicInsert
public class AttachRequest extends CreateAuditChildEntity {
    @Getter
    @OneToMany(mappedBy = "attachRequest", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachFile> attachFiles;

    //
    //
    //

    public AttachRequest(@NonNull final List<MultipartFile> files) {
        addFiles(files);
    }

    //
    //
    //

    private void addFiles(@NonNull final List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            throw PlatformException.builder().status(PlatformStatus.UNAVAILABLE_FILE).build();
        }

        attachFiles = files.stream().map(AttachFile::new).map(this::bind).collect(Collectors.toList());
    }

    private @NonNull AttachFile bind(@NonNull final AttachFile attachFile) {
        attachFile.setAttachRequest(this);
        return attachFile;
    }
}