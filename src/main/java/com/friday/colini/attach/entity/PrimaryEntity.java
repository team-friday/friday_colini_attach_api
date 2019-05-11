package com.friday.colini.attach.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class PrimaryEntity {
    abstract long getId();
    abstract boolean isEqualsId(final long another);
}
