package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Alias("celebrity")
@ToString
public class Celebrity extends BaseEntity {

    private long celebrityId;
    private String name;
    private String imageUrl;

    private String useAt = "Y";

}
