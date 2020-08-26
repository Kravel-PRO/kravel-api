package com.kravel.server.api.dto.media;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("rawPlaceRelatedMedia")
public class RawPlaceRelatedMediaDTO {
    private long articleId;
    private String subject;
    private String imgUrl;
    private String celebrities;
}
