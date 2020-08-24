package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("article")
public class Article {
    private long articleId;
    private String location;
    private String creator;

    private double latitude;
    private double longitude;
    private double grade;
    private double weight;

    private List<ArticleReview> articleReviewList;
    private ArticleInfo articleInfo;

    private LocalDateTime createDe;
    private LocalDateTime updateDe;
    private String useAt;
}
