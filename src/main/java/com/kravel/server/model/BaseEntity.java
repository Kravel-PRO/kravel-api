package com.kravel.server.model;

import com.kravel.server.model.member.Member;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedBy
    private long createdBy;

    @LastModifiedBy
    private long lastModifiedBy;

}
