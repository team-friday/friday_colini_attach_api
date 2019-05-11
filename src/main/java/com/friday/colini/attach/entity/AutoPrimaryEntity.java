package com.friday.colini.attach.entity;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class AutoPrimaryEntity extends PrimaryEntity {
    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //
    //
    //

    @Override
    boolean isEqualsId(final long another) {
        return id == another;
    }
}
