package com.friday.colini.attach.entity;

import com.friday.colini.attach.utils.BeanUtils;
import com.friday.colini.attach.utils.DateConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class CreateAuditChildEntity extends AutoChildEntity {
    @Column(nullable = false, updatable = false)
    @CreatedDate @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //
    //
    //

    Optional<LocalDateTime> getCreatedAt() {
        return BeanUtils.getBean(DateConverter.class).dateToLocalDateTime(createdAt);
    }
}
