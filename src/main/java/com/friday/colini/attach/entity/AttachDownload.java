package com.friday.colini.attach.entity;

import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table
@Entity
@DynamicInsert
@DynamicUpdate
public class AttachDownload extends UpdateAuditManualEntity {
    @Getter
    @Column(nullable = false)
    private long count;

    //
    //
    //

    // Using JPA
    private AttachDownload() {
        super(0);
    }

    AttachDownload(final long id) {
        super(id);
    }
}
