package com.kravel.server.api.dto.review;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("reviewOverviewDTO")
public class ReviewOverviewDTO {
    private long reviewId;
    private String imageUrl;
}
