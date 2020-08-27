package com.kravel.server.service;

import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.mapper.CelebrityMapper;
import com.kravel.server.mapper.MediaMapper;
import com.kravel.server.mapper.ReviewMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.mapping.ReviewLikeQueryRepository;
import com.kravel.server.model.review.Review;
import com.kravel.server.model.review.ReviewQueryRepository;
import com.kravel.server.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final ReviewMapper reviewMapper;
    private final MediaMapper mediaMapper;
    private final CelebrityMapper celebrityMapper;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    private final ReviewLikeQueryRepository reviewLikeQueryRepository;

    public ReviewOverviewDTO findAllReview(long placeId, Pageable pageable) throws Exception {

        List<Review> reviews = reviewQueryRepository.findAllReview(placeId, pageable);
        long totalCount = reviewQueryRepository.findAllReviewCount(placeId);

        if (reviews.isEmpty() || totalCount == 0) {
            throw new NotFoundException("🔥 error: is not exist review");
        }

        ReviewOverviewDTO reviewOverviewDTO = ReviewOverviewDTO.builder()
                .reviews(reviews.stream().map(ReviewDTO::fromEntity).collect(Collectors.toList()))
                .totalCount(totalCount)
                .build();


        return reviewOverviewDTO;
    }

    public ReviewDetailDTO findReviewDetailById(long reviewId, long memberId) throws Exception {

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist review");
        }

        long likeCount = reviewLikeQueryRepository.findReviewLikeCount(reviewId);
        boolean like = reviewLikeQueryRepository.checkReviewLikeExist(reviewId, memberId);

        ReviewDetailDTO reviewDetailDTO = ReviewDetailDTO.builder()
                .reviewId(optionalReview.get().getId())
                .imageUrl(optionalReview.get().getImageUrl())
                .likeCount(likeCount)
                .like(like)
                .build();

        return reviewDetailDTO;
    }
//=============================
    public List<ReviewOverviewDTO> findAllReviewByCelebrity(Map<String, Object> param) throws Exception {

        List<ReviewOverviewDTO> reviewOverviewDTOs = reviewMapper.findAllReviews(param);
        if (reviewOverviewDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist reviews");
        }

        return reviewOverviewDTOs;
    }

    public List<String> saveReview(MultipartFile file, long placeId, long memberId, long mediaId) throws Exception {
        // TODO place랑 관련된 media id 찾아서 review에 넣어주기

        Review review = Review.builder()
                .memberId(memberId)
                .mediaId(mediaId)
                .build();

        review.saveImage(s3Uploader, file);

        if (review.getImageUrl().isEmpty()) {
            throw new InvalidRequestException("🔥 error: image upload is failed");
        }


    }

    public boolean saveReviewToDatabase(Map<String, Object> param) throws Exception {

        Review review = reviewMapper.findArticleReviewById(param);

        param.put("mediaId", review.getMediaId());
        param.put("celebrityId", review.getCelebrityId());

        reviewMapper.saveReview(param);
        return reviewMapper.saveReviewImg(param) != 0;
    }

    public boolean handleReviewLike(Map<String, Object> param) throws Exception {
        int savedLike = reviewMapper.checkExistReviewLike(param);

        if ((boolean) param.get("likeState") && savedLike == 0) {
            return reviewMapper.saveReviewLike(param) != 0;

        } else if (savedLike >= 1) {
            return reviewMapper.removeReviewLike(param) != 0;

        } else  {
            throw new InvalidRequestException("It is not valid likeState");
        }
    }

}
