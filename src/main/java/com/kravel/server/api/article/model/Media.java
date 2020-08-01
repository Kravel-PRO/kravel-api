package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Alias("media")
public class Media {
    private long mediaId;
    private long creator;
    private String mediaName;
    private String contents;
    private String imgUrl;
    private String openYear;
    private LocalDateTime createDe;
    private LocalDateTime updateDe;
    private String useAt;
}
