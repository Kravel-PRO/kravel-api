package com.kravel.server.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("reviewDetailDTO")
public class ReviewDetailDTO {

    private long reviewId;
    private String imageUrl;

    private int likeCount;
    private boolean likeState;
}
