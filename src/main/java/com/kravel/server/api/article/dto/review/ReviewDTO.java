package com.kravel.server.api.article.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleReviewDTO")
public class ReviewDTO {

    private long reviewId;
    private List<ImgDTO> imgDTOs;

    private int likeCnt;
    private boolean likeState;
}
