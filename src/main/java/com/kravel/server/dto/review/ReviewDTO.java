package com.kravel.server.dto.review;

import com.kravel.server.model.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReviewDTO {
    private long reviewId;
    private String imageURl;
    private double grade;
    private long likeCount;

    public static ReviewDTO fromEntity(Review entity) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(entity.getId());
        reviewDTO.setImageURl(entity.getImageUrl());

        return reviewDTO;
    }
}
