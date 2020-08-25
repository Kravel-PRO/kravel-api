package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class BaseEntity {

    private String createdBy;

    private String lastModifiedBy;
}
