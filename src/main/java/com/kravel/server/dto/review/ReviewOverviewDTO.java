package com.kravel.server.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ReviewOverviewDTO {

    private List<ReviewDTO> reviews;
    private long totalCount;

    @Builder
    public ReviewOverviewDTO(List<ReviewDTO> reviews, long totalCount) {
        this.reviews = reviews;
        this.totalCount = totalCount;
    }
}
