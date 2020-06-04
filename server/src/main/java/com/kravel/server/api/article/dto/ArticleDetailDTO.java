package com.kravel.server.api.article.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleDetailDTO")
public class ArticleDetailDTO {
    private long articleId;

    private String subject;
    private String contents;

    private String location;
    private String latitude;
    private String longitude;

    private float grade;
    private float weight;

    private long mediaId;
    private String mediaName;

    private int reviewCnt;
    private List<ArticleReviewListDTO> reviewList;
    private List<CelebrityDTO> celebrityList;

}
