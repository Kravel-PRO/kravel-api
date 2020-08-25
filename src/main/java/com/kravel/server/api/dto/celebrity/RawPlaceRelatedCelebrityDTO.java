package com.kravel.server.api.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("rawPlaceRelatedCelebrityDTO")
public class RawPlaceRelatedCelebrityDTO {
    private long articleId;
    private String subject;
    private String imageUrl;
    private String mediaName;
    private String celebrities;
}
