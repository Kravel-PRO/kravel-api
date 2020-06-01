package com.kravel.server.api.article.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleSub {
    private long subId;

    private long articleId;
    private String typeCode;
    private String url;
}
