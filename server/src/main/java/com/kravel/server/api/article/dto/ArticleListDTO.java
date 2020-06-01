package com.kravel.server.api.article.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("articleListDTO")
public class ArticleListDTO {
    private long articleId;
    private String subject;
    private String img;
    private int mediaCnt;
    private int celebrityCnt;
}
