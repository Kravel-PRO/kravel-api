package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/reviews")
    public ResponseEntity<Message> findAll(@PageableDefault Pageable pageable,
                                           Authentication authentication) throws Exception {

        Speech speech = claimExtractor.getSpeech(authentication);

        Page<ReviewDetailDTO> reviewDTOs = reviewService.findAll(pageable, speech);
        return ResponseEntity.ok(new Message(reviewDTOs));
    }

    @GetMapping("/api/places/{placeId}/reviews")
    public ResponseEntity<Message> findAllByPlace(@PathVariable("placeId") long placeId,
                                                  @PageableDefault Pageable pageable,
                                                  Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/reviews");
        Speech speech = claimExtractor.getSpeech(authentication);

        Page<ReviewDetailDTO> reviewDTOs = reviewService.findAllByPlace(placeId, pageable, speech);
        return ResponseEntity.ok().body(new Message(reviewDTOs));
    }

    @GetMapping("/api/places/{placeId}/reviews/{reviewId}")
    public ResponseEntity<Message> findReviewDetailById(@PathVariable("placeId") long placeId,
                                                        @PathVariable("reviewId") long reviewId,
                                                        Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/reviews/{reviewId}");
        long memberId = claimExtractor.getMemberId(authentication);

        ReviewDetailDTO reviewDetailDTO = reviewService.findReviewDetailById(reviewId, memberId);
        return ResponseEntity.ok(new Message(reviewDetailDTO));
    }

    @PostMapping("/api/places/{placeId}/reviews")
    public ResponseEntity<Message> saveReview(@PathVariable("placeId") int placeId,
                                              @RequestParam("file") MultipartFile file,
                                              Authentication authentication) throws Exception {
        log.info("🎉 GET /api/places/{placeId}/reviews");

        long memberId = claimExtractor.getMemberId(authentication);
        long reviewId = reviewService.saveReview(file, placeId, memberId);

        return ResponseEntity.ok(new Message(reviewId));
    }

    @GetMapping("/api/medias/{mediaId}/reviews")
    public ResponseEntity<Message> findAllReviewsByMedia(@PathVariable("mediaId") long mediaId,
                                                         @PageableDefault Pageable pageable,
                                                         Authentication authentication) throws Exception {

        Speech speech = claimExtractor.getSpeech(authentication);
        Page<ReviewDetailDTO> reviewDetailDTOs = reviewService.findAllByMedia(mediaId, pageable, speech);
        return ResponseEntity.ok(new Message(reviewDetailDTOs));
    }

    @PostMapping("/api/places/{placeId}/reviews/{reviewId}/likes")
    public ResponseEntity<Message> handleReviewLike(@PathVariable("placeId") long placeId,
                                            @PathVariable("reviewId") long reviewId,
                                            @RequestBody ReviewLikeDTO reviewLikeDTO,
                                            Authentication authentication) throws Exception{

        log.info("🎉 GET /api/places/{placeId}/reviews/{reviewId}/likes");


        long memberId = claimExtractor.getMemberId(authentication);
        long reviewLikeId = reviewService.handleReviewLike(placeId, reviewId, memberId, reviewLikeDTO);

        return ResponseEntity.ok(new Message(reviewLikeId));
    }

    @GetMapping("/api/celebrities/{celebrityId}/reviews")
    public ResponseEntity<Message> findAllReviewByCelebrity(@PathVariable("celebrityId") long celebrityId,
                                                            @PageableDefault Pageable pageable,
                                                            Authentication authentication) throws Exception {

        Speech speech = claimExtractor.getSpeech(authentication);
        Page<ReviewDetailDTO> reviewDTOs = reviewService.findAllByCelebrity(celebrityId, pageable, speech);
        return ResponseEntity.ok(new Message(reviewDTOs));
    }

    @GetMapping("/api/member/reviews")
    public ResponseEntity<Message> findAllByMember(@PageableDefault Pageable pageable,
                                                   Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        Speech speech = claimExtractor.getSpeech(authentication);
        Page<ReviewDetailDTO> reviewDetailDTOs = reviewService.findAllByMember(memberId, speech, pageable);
        return ResponseEntity.ok(new Message(reviewDetailDTOs));
    }

    @GetMapping("/api/member/reviews/likes")
    public ResponseEntity<Message> findAllReviewLikeById(@PageableDefault Pageable pageable,
                                                         Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        Page<ReviewDetailDTO> reviewDetailDTOs = reviewService.findAllReviewLikeById(memberId, pageable);
        return ResponseEntity.ok(new Message(reviewDetailDTOs));
    }
}
