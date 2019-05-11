package com.friday.colini.attach.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class AutoChildEntity extends ChildEntity {
    AutoChildEntity() {
        // TODO: get spring security holder
        super(0);
    }
}
