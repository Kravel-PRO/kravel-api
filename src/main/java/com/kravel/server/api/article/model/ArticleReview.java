package com.kravel.server.api.article.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class ArticleReview {
    private long reviewId;

    // fk
    private long memberId;
    private long articleId;
    private long mediaId;
    private long celebrityId;

    private float grade;

    private LocalDateTime createDe;
    private LocalDateTime updateDe;
    private String useAt;

}
