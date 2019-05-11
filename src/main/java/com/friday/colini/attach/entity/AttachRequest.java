package com.friday.colini.attach.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // Using JPA
@Table
@Entity
@DynamicInsert
public class AttachRequest extends CreateAuditChildEntity {
    @Getter
    @OneToMany(mappedBy = "attachRequest", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachFile> attachFiles;
}