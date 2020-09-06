package com.kravel.server.dto.review;

import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Getter @Setter
@NoArgsConstructor
public class ReviewDetailDTO {

    private long reviewId;
    private String imageUrl;
    private long likeCount;
    private boolean like;
    private String createdDate;


    @Builder
    public ReviewDetailDTO(long reviewId, String imageUrl, long likeCount, boolean like) {
        this.reviewId = reviewId;
        this.imageUrl = imageUrl;
        this.likeCount = likeCount;
        this.like = like;
    }

    public static ReviewDetailDTO fromEntity(Review entity) {
        ReviewDetailDTO reviewDetailDTO = new ReviewDetailDTO();
        reviewDetailDTO.setReviewId(entity.getId());
        reviewDetailDTO.setImageUrl(entity.getImageUrl());
        reviewDetailDTO.setCreatedDate(entity.getCreatedDate().toString().substring(0, 10));
        reviewDetailDTO.setLike(Optional.ofNullable(entity.getReviewLikes())
                .orElse(new ArrayList<>()).stream()
                .findFirst().isPresent());
        return reviewDetailDTO;
    }
}
