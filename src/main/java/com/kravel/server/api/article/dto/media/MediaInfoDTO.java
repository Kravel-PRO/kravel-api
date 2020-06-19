package com.kravel.server.api.article.dto.media;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("mediaInfoDTO")
public class MediaInfoDTO {
    private long mediaId;
    private String mediaName;
    private String imgUrl;
    private String contents;
    private String openYear;
}
