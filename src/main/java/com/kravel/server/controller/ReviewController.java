package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
import com.kravel.server.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/api/places/{placeId}/reviews")
    public ResponseEntity<Message> findAllReview(@PathVariable("placeId") long placeId,
                                                 @PageableDefault Pageable pageable,
                                                 Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/reviews");

        ReviewOverviewDTO reviewOverviewDTO = reviewService.findAllReview(placeId, pageable);
        return ResponseEntity.ok().body(new Message(reviewOverviewDTO));
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

    @PostMapping("/api/places/{placeId}/reviews/{reviewId}")
    public ResponseEntity<Message> handleReviewLike(@PathVariable("placeId") long placeId,
                                            @PathVariable("reviewId") long reviewId,
                                            @RequestBody ReviewLikeDTO reviewLikeDTO,
                                            Authentication authentication) throws Exception{

        log.info("🎉 GET /api/places/{placeId}/reviews/{reviewId}");

        Map<String, Object> param = new HashMap<String, Object>();

        long memberId = claimExtractor.getMemberId(authentication);
        long reviewLikeId = reviewService.handleReviewLike(placeId, reviewId, memberId, reviewLikeDTO);

        return ResponseEntity.ok(new Message(reviewLikeId));
    }

}
