package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleInfo {
    private long infoId;
    private long articleId;

    private String subject;
    private String contents;
    private String infoLangu;
}
