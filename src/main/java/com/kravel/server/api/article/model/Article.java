package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

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
    private float grade;
    private float weight;

    private List<ArticleReview> articleReviewList;
    private ArticleInfo articleInfo;

    private Date createDe;
    private Date updateDe;
    private String useAt;
}
