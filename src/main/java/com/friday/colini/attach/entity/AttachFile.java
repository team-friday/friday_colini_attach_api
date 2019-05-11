package com.friday.colini.attach.entity;

import com.friday.colini.attach.type.UploadType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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
}
