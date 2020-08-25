package com.kravel.server.api.dto.article;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("articleReviewImgDTO")
public class ArticleReviewImgDTO {
    private long articleId;
    private String imgUrl;
}