package com.kravel.server.api.article.dto.media;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("mediaListDTO")
public class MediaListDTO {

    private long mediaId;
    private String mediaName;
    private String openYear;
}
