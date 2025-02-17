package com.kravel.server.service;

import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.mapping.ReviewLikeQueryRepository;
import com.kravel.server.model.mapping.ReviewLikeRepository;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceRepository;
import com.kravel.server.model.review.Review;
import com.kravel.server.model.review.ReviewQueryRepository;
import com.kravel.server.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    public Page<ReviewDetailDTO> findAll(long memberId, Pageable pageable, Speech speech) throws Exception {
        Page<Review> reviews = reviewQueryRepository.findAll(pageable);

        reviews.forEach(review -> {
            review.getPlace().findTagSpeech(speech);
            review.getPlace().findInfoSpeech(speech);
        });
        return getReviewDetailDTOs(reviews, memberId);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDetailDTO> findAllByPlace(long placeId, long memberId, Pageable pageable, Speech speech) throws Exception {
        Page<Review> reviews = reviewQueryRepository.findAllByPlace(placeId, pageable);

        reviews.forEach(review -> {
            review.getPlace().findTagSpeech(speech);
            review.getPlace().findInfoSpeech(speech);
        });
        return getReviewDetailDTOs(reviews, memberId);
    }

    @Transactional(readOnly = true)
    public ReviewDetailDTO findReviewDetailById(long reviewId, long memberId) throws Exception {

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        long likeCount = reviewLikeQueryRepository.findLikeCountByReview(reviewId);
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

    @Transactional(readOnly = true)
    public Page<ReviewDetailDTO> findAllByCelebrity(long celebrityId, long memberId, Pageable pageable, Speech speech) throws Exception {

        Page<Review> reviews = reviewQueryRepository.findAllReviewByCelebrity(celebrityId, pageable);

        reviews.forEach(review -> {
            review.getPlace().findTagSpeech(speech);
            review.getPlace().findInfoSpeech(speech);
        });
        return getReviewDetailDTOs(reviews, memberId);
    }

    public long saveReview(MultipartFile file, long placeId, long memberId) throws Exception {
        if (file.isEmpty()) {
            throw new InvalidRequestException("🔥 error: is not exist image file");
        }

        Member savedMember = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("🔥 error: not found member"));
        Place savedPlace = placeRepository
                .findById(placeId)
                .orElseThrow(() -> new NotFoundException("🔥 error: not found place"));
        if (file.isEmpty()) {
            throw new InvalidRequestException("🔥 error: is not exist image file");
        }
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
            ReviewLike reviewLike = savedLike.get();
            if (reviewLike.getMember().getId() == memberId) {
                reviewLikeRepository.delete(reviewLike);
            }
            return -1;

        } else  {
            throw new InvalidRequestException("🔥 error: it is not valid like state");
        }
    }

    @Transactional(readOnly = true)
    public Page<ReviewDetailDTO> findAllByMedia(long mediaId, long memberId, Pageable pageable, Speech speech) throws Exception {
        Page<Review> reviews = reviewQueryRepository.findAllByMedia(mediaId, pageable);

        reviews.forEach(review -> {
            review.getPlace().findTagSpeech(speech);
            review.getPlace().findInfoSpeech(speech);
        });
        return getReviewDetailDTOs(reviews, memberId);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDetailDTO> findAllByMember(long memberId, Speech speech, Pageable pageable) {
        Page<Review> reviews = reviewQueryRepository.findAllByMember(memberId, pageable);

        reviews.forEach(review ->
                review.getPlace().findInfoSpeech(speech)
        );
        return getReviewDetailDTOs(reviews, memberId);
    }

    public Page<ReviewDetailDTO> findAllReviewLikeById(long memberId, Pageable pageable) {
        Page<Review> reviews = reviewQueryRepository.findAllReviewLikeById(memberId, pageable);

        return getReviewDetailDTOs(reviews, memberId);
    }

    private Page<ReviewDetailDTO> getReviewDetailDTOs(Page<Review> reviews, long memberId) {
        Page<ReviewDetailDTO> reviewDetailDTOs = reviews.map(new Function<Review, ReviewDetailDTO>() {
            @Override
            public ReviewDetailDTO apply(Review review) {
                ReviewDetailDTO reviewDetailDTO = ReviewDetailDTO.fromEntity(review);
                reviewDetailDTO.setLike(review.getReviewLikes().stream().anyMatch(reviewLike -> reviewLike.getMember().getId() == memberId));
                long likeCountByReview = reviewLikeQueryRepository.findLikeCountByReview(reviewDetailDTO.getReviewId());
                reviewDetailDTO.setLikeCount(likeCountByReview);
                return reviewDetailDTO;
            }
        });

        return reviewDetailDTOs;
    }

    public void deleteById(long memberId, long reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist review"));
        if (review.getMember().getId() == memberId) {
            s3Uploader.removeS3Object(review.getImageUrl());
            reviewRepository.delete(review);
        } else  {
            throw new InvalidRequestException("🔥 error: is not valid request");
        }
    }
}
