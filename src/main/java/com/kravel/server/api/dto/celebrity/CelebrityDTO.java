package com.kravel.server.api.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("celebrityDTO")
public class CelebrityDTO {
    private long celebrityId;
    private String celebrityName;
    private String imgUrl;
}
