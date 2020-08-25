package com.kravel.server.api.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("exCelebrityArticleDTO")
public class ExCelebrityArticleDTO {
    private long articleId;
    private String subject;
    private String imgUrl;
    private String mediaName;
    private String celebrities;
}
