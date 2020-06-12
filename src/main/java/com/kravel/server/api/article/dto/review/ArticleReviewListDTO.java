package com.kravel.server.api.article.dto.review;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("articleReviewListDTO")
public class ArticleReviewListDTO {
    private long reviewId;
    private String representImgUrl;
}
