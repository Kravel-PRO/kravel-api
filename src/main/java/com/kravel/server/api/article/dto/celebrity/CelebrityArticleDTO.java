package com.kravel.server.api.article.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("celebrityArticleDTO")
public class CelebrityArticleDTO {
    private long articleId;
    private String subject;
    private String imgUrl;
    private String celebrities;
}
