package com.kravel.server.api.article.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleReviewDTO")
public class ArticleReviewDTO {

    private long reviewId;
    private String repreRwImg;
    private List<String> rwImgList;
    private int likeCnt;
    private int likeState;
}
