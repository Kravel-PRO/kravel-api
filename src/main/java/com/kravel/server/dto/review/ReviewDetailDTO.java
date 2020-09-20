package com.kravel.server.dto.review;

import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceTitleDTO;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.Tag;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@NoArgsConstructor
public class ReviewDetailDTO {

    private long reviewId;
    private String imageUrl;
    private long likeCount;
    private boolean like;
    private String createdDate;
    private PlaceTitleDTO place;
    private String modifiedDate;

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
        reviewDetailDTO.setModifiedDate(entity.getModifiedDate().toString().substring(0, 10));

        reviewDetailDTO.setPlace(PlaceTitleDTO.fromEntity(entity.getPlace()));
        return reviewDetailDTO;
    }
}
