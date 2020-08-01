package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Alias("celebrity")
@ToString
public class Celebrity {

    private long celebrityId;
    private long creator;
    private String celebrityName;
    private LocalDateTime createDe;
    private LocalDateTime updateDe;
    private String useAt;
    private String imgUrl;

}
