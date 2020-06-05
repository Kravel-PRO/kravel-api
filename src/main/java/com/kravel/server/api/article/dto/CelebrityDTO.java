package com.kravel.server.api.article.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("celebrityDTO")
public class CelebrityDTO {
    private String celebrityId;
    private String celebrityName;
}
