package com.friday.colini.attach.entity;

import com.friday.colini.attach.utils.BeanUtils;
import com.friday.colini.attach.utils.DateConverter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class UpdateAuditManualEntity extends ManualPrimaryEntity {
    @Column(nullable = false)
    @LastModifiedDate @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    UpdateAuditManualEntity(final long id) {
        super(id);
    }

    Optional<LocalDateTime> getUpdatedAt() {
        return BeanUtils.getBean(DateConverter.class).dateToLocalDateTime(updatedAt);
    }
}
