package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@Alias("media")
public class Media {
    private long mediaId;
    private long creator;
    private String mediaName;
    private String explain;
    private String imgUrl;
    private String openYear;
    private Date createDe;
    private Date updateDe;
    private String useAt;
}
