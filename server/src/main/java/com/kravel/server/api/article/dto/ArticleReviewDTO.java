package com.kravel.server.api.article.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ArticleReviewDTO {

    private long reviewId;
    private String repreRwImg;
    private List<String> rwImgList;
}
