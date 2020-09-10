package com.kravel.server.service;

import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.mapping.ReviewLikeQueryRepository;
import com.kravel.server.model.mapping.ReviewLikeRepository;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.PlaceRepository;
import com.kravel.server.model.review.Review;
import com.kravel.server.model.review.ReviewQueryRepository;
import com.kravel.server.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

    public Page<ReviewDetailDTO> findAll(Pageable pageable, String speech) throws Exception {
        Page<Review> reviews = reviewQueryRepository.findAll(pageable);
        reviews.forEach(review -> review.getPlace().findTagSpeech(speech));
        return (Page<ReviewDetailDTO>) reviews.map(new Function<Review, ReviewDetailDTO>() {
            @Override
            public ReviewDetailDTO apply(Review review) {
                return ReviewDetailDTO.fromEntity(review);
            }
        });
    }

    public Page<ReviewDTO> findAllByPlace(long placeId, boolean likeCount, Pageable pageable) throws Exception {

        Page<Review> reviews = reviewQueryRepository.findAllByPlace(placeId, pageable);
        Page<ReviewDTO> reviewDTOs = (Page<ReviewDTO>) reviews.map(new Function<Review, ReviewDTO>() {
            @Override
            public ReviewDTO apply(Review review) {
                return ReviewDTO.fromEntity(review);
            }
        });

        if (likeCount) {
            for (var reviewDTO : reviewDTOs) {
                reviewDTO.setLikeCount(reviewLikeQueryRepository.findCountByReview(reviewDTO.getReviewId()));
            }
        }

        return reviewDTOs;
    }

    public ReviewDetailDTO findReviewDetailById(long reviewId, long memberId) throws Exception {

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist review");
        }

        long likeCount = reviewLikeQueryRepository.findCountByReview(reviewId);
        ReviewLike reviewLike = reviewLikeQueryRepository
                .checkReviewLikeExist(reviewId, memberId)
                .orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));

        ReviewDetailDTO reviewDetailDTO = ReviewDetailDTO.builder()
                .reviewId(optionalReview.get().getId())
                .imageUrl(optionalReview.get().getImageUrl())
                .likeCount(likeCount)
                .like(reviewLike.getId() > 0)
                .build();

        return reviewDetailDTO;
    }

    public Page<ReviewDTO> findAllReviewByCelebrity(long celebrityId, Pageable pageable) throws Exception {

        Page<Review> reviews = reviewQueryRepository.findAllReviewByCelebrity(celebrityId, pageable);
        return reviews.map(new Function<Review, ReviewDTO>() {
            @Override
            public ReviewDTO apply(Review review) {
                return ReviewDTO.fromEntity(review);
            }
        });
    }

    public long saveReview(MultipartFile file, long placeId, long memberId) throws Exception {
        Member savedMember = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("🔥 error: not found member"));
        Place savedPlace = placeRepository
                .findById(placeId)
                .orElseThrow(() -> new NotFoundException("🔥 error: not found place"));

        Review review = Review.builder()
                .member(savedMember)
                .place(savedPlace)
                .media(Optional.ofNullable(savedPlace.getMedia()).orElse(null))
                .celebrities(Optional.ofNullable(savedPlace.getPlaceCelebrities())
                        .orElse(new ArrayList<>()).stream()
                        .map(PlaceCelebrity::getCelebrity)
                        .collect(Collectors.toList()))
                .build();

        review.saveImage(s3Uploader, file);

        return reviewRepository.save(review).getId();
    }

    public long handleReviewLike(long placeId, long reviewId, long memberId, ReviewLikeDTO reviewLikeDTO) throws Exception {
        Optional<ReviewLike> savedLike = reviewLikeQueryRepository.checkReviewLikeExist(reviewId, memberId);

        if (reviewLikeDTO.isLike()) {
            if (savedLike.isPresent()) {
                throw new InvalidRequestException("🔥 error: like is already exist");
            }

            Member member = Member.builder().id(memberId).build();
            Review review = Review.builder().id(reviewId).build();

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

    public Page<ReviewDTO> findAllByMedia(long mediaId, Pageable pageable) throws Exception {
        Page<Review> reviews = reviewQueryRepository.findAllByMedia(mediaId, pageable);
        return reviews.map(new Function<Review, ReviewDTO>() {
            @Override
            public ReviewDTO apply(Review review) {
                return ReviewDTO.fromEntity(review);
            }
        });
    }

    public Page<ReviewDetailDTO> findAllByMember(long memberId, String speech, Pageable pageable, boolean likeCount) {
        Page<Review> reviews = reviewQueryRepository.findAllByMember(memberId, pageable);
        for (var review : reviews) {
            PlaceInfo placeInfo = review.getPlace().getPlaceInfos().stream()
                    .filter(info -> info
                            .getSpeech()
                            .equals(speech))
                    .findFirst()
                    .orElseThrow(() -> new InvalidRequestException("is not exist place info"));

            List<PlaceInfo> placeInfos = new ArrayList<>();
            placeInfos.add(placeInfo);
            review.getPlace().changePlaceInfo(placeInfos);
        }

        return getReviewDetailDTOs(likeCount, reviews);
    }

    public Page<ReviewDetailDTO> findAllReviewLikeById(long memberId, Pageable pageable, boolean likeCount) {
        Page<Review> reviews = reviewQueryRepository.findAllReviewLikeById(memberId, pageable);
        return getReviewDetailDTOs(likeCount, reviews);
    }

    private Page<ReviewDetailDTO> getReviewDetailDTOs(boolean likeCount, Page<Review> reviews) {
        Page<ReviewDetailDTO> reviewDetailDTOs = reviews.map(new Function<Review, ReviewDetailDTO>() {
            @Override
            public ReviewDetailDTO apply(Review review) {
                return ReviewDetailDTO.fromEntity(review);
            }
        });
        if (likeCount) {
            for (var reviewDetailDTO : reviewDetailDTOs) {
                reviewDetailDTO.setLikeCount(reviewLikeQueryRepository.findCountByReview(reviewDetailDTO.getReviewId()));
            }
        }

        return reviewDetailDTOs;
    }
}
