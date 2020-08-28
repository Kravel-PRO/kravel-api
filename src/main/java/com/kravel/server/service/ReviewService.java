package com.kravel.server.service;

import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.mapping.ReviewLikeQueryRepository;
import com.kravel.server.model.mapping.ReviewLikeRepository;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceRepository;
import com.kravel.server.model.review.Review;
import com.kravel.server.model.review.ReviewQueryRepository;
import com.kravel.server.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final S3Uploader s3Uploader;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    private final ReviewLikeQueryRepository reviewLikeQueryRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final ReviewLikeRepository reviewLikeRepository;

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

        long likeCount = reviewLikeQueryRepository.findReviewLikeCountById(reviewId);
        ReviewLike reviewLike = reviewLikeQueryRepository.checkReviewLikeExist(reviewId, memberId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));

        ReviewDetailDTO reviewDetailDTO = ReviewDetailDTO.builder()
                .reviewId(optionalReview.get().getId())
                .imageUrl(optionalReview.get().getImageUrl())
                .likeCount(likeCount)
                .like(reviewLike.getId() > 0)
                .build();

        return reviewDetailDTO;
    }
    public ReviewOverviewDTO findAllReviewByCelebrity(long celebrityId) throws Exception {

        List<ReviewDTO> reviewDTOs = reviewQueryRepository.findAllReviewByCelebrity(celebrityId).stream().map(ReviewDTO::fromEntity).collect(Collectors.toList());
        long totalCount = reviewLikeQueryRepository.findReviewLikeCountByCelebrity(celebrityId);
        if (reviewDTOs.size() == 0 || totalCount == 0) {
            throw new NotFoundException("🔥 error: is not exist review");
        }

        ReviewOverviewDTO reviewOverviewDTO = ReviewOverviewDTO.builder()
                .reviews(reviewDTOs)
                .totalCount(totalCount)
                .build();

        return reviewOverviewDTO;
    }

    public long saveReview(MultipartFile file, long placeId, long memberId) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Place> optionalPlace = placeRepository.findById(placeId);

        if (optionalMember.isEmpty() || optionalPlace.isEmpty()) {
            throw new NotFoundException("🔥 error: not found member or place");
        }
        Review review = Review.builder()
                .member(optionalMember.get())
                .place(optionalPlace.get())
                .build();

        review.saveImage(s3Uploader, file);

        Media media = optionalPlace.get().getMedia();
        if (media.getId() > 0) {
            review.changeMedia(media);
        }

        return reviewRepository.save(review).getId();
    }

    public long handleReviewLike(long placeId, long reviewId, long memberId, ReviewLikeDTO reviewLikeDTO) throws Exception {
        Optional<ReviewLike> savedLike = reviewLikeQueryRepository.checkReviewLikeExist(reviewId, memberId);

        if (reviewLikeDTO.isLike()) {
            if (savedLike.isPresent()) {
                throw new InvalidRequestException("🔥 error: like is already exist");
            }

            Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist review"));

            ReviewLike reviewLike = ReviewLike.builder()
                    .member(member)
                    .review(review)
                    .build();

            return reviewLikeRepository.save(reviewLike).getId();

        } else if (!reviewLikeDTO.isLike()) {
            if (savedLike.isEmpty()) {
                throw new InvalidRequestException("🔥 error: like is not exist");
            }

            reviewLikeRepository.delete(savedLike.get());
            return -1;

        } else  {
            throw new InvalidRequestException("🔥 error: it is not valid like state");
        }
    }

}
