package com.friday.colini.attach.entity;

import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class ManualPrimaryEntity extends PrimaryEntity {
    @Getter
    @Id
    private long id;

    //
    //
    //

    ManualPrimaryEntity(final long id) {
        this.id = id;
    }

    //
    //
    //

    @Override
    boolean isEqualsId(final long another) {
        return id == another;
    }
}
