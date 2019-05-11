package com.friday.colini.attach.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

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
        // TODO: add files
    }
}