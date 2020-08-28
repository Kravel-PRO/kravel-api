package com.kravel.server.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter @Setter
public class ReviewDetailDTO {

    private long reviewId;
    private String imageUrl;

    private long likeCount;
    private boolean like;

    @Builder
    public ReviewDetailDTO(long reviewId, String imageUrl, long likeCount, boolean like) {
        this.reviewId = reviewId;
        this.imageUrl = imageUrl;
        this.likeCount = likeCount;
        this.like = like;
    }
}
