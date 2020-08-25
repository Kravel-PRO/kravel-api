package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class BaseTimeEntity {

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
