package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Alias("media")
public class Media extends BaseEntity {
    private long mediaId;
    private String mediaName;
    private String contents;
    private String imageUrl;
    private String year;
    private String useAt = "Y";
}
