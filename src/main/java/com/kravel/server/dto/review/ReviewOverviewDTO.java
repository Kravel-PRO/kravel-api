package com.kravel.server.dto.review;

import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter @Setter
@Alias("reviewOverviewDTO")
public class ReviewOverviewDTO {

    private List<ReviewDTO> reviews;
    private long totalCount;

    @Builder
    public ReviewOverviewDTO(List<ReviewDTO> reviews, long totalCount) {
        this.reviews = reviews;
        this.totalCount = totalCount;
    }
}
