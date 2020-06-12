package com.kravel.server.api.article.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("celebrityDTO")
public class CelebrityDTO {
    private long celebrityId;
    private String celebrityName;
    private String imgUrl;
}
