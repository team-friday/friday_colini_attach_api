package com.friday.colini.attach.entity;

import com.friday.colini.attach.entity.converter.LocalDateTimeConveter;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class CreateAuditChildEntity extends AutoChildEntity {
    @Getter
    @Column(nullable = false, updatable = false)
    @Convert(converter = LocalDateTimeConveter.class)
    @CreatedDate
    private LocalDateTime createdAt;
}
