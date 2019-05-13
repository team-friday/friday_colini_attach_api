package com.friday.colini.attach.entity;

import com.friday.colini.attach.properties.FileProperties;
import com.friday.colini.attach.type.UploadType;
import com.friday.colini.attach.utils.BeanUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // Using JPA
@Table
@Entity
@DynamicInsert
public class AttachFile extends AutoPrimaryEntity {
    @Getter
    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String uploadPath;

    @Getter
    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private UploadType uploadType;

    @Setter
    @ManyToOne
    private AttachRequest attachRequest;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    private AttachDownload attachDownload;

    //
    //
    //

    AttachFile(@NonNull final MultipartFile file) {
        originalName = file.getOriginalFilename();
        contentType = file.getContentType();
        uploadType = BeanUtils.getBean(FileProperties.class).getUploadType();

        // TODO: upload
        uploadPath = uploadType.upload(file);
    }

    @PostPersist
    private void onPostPersist() {
        initAttachDownload();
    }

    //
    //
    //

    public long getDownloadCount() {
        return attachDownload.getCount();
    }

    public @Nullable LocalDateTime getLastDownloadAt() {
        return attachDownload.getUpdatedAt();
    }

    public @Nullable LocalDateTime getCreatedAt() {
        return attachRequest.getCreatedAt();
    }

    public boolean isNotEqualsAttachRequestId(long attachRequestId) {
        return !attachRequest.isEqualsId(attachRequestId);
    }

    //
    //
    //

    private void initAttachDownload() {
        attachDownload = new AttachDownload(getId());
    }
}
