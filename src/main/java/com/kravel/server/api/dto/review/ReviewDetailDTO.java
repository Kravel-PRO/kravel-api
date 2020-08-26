package com.kravel.server.api.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

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
