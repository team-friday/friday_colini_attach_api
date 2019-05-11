package com.friday.colini.attach.entity;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class ChildEntity extends AutoPrimaryEntity {
    @Column(nullable = false)
    private long owner;

    //
    //
    //

    ChildEntity(final long owner) {
        this.owner = owner;
    }

    //
    //
    //

    public boolean isOwner(@NonNull final PrimaryEntity ownerEntity) {
        return ownerEntity.isEqualsId(owner);
    }
}
