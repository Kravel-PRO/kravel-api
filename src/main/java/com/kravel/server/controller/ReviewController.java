package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewDetailDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.dto.review.ReviewLikeDTO;
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
    public ResponseEntity<Message> findAll(@PageableDefault Pageable pageable) throws Exception {

        Page<ReviewDTO> reviewDTOs = reviewService.findAll(pageable);
        return ResponseEntity.ok(new Message(reviewDTOs));
    }

    @GetMapping("/api/places/{placeId}/reviews")
    public ResponseEntity<Message> findAllByPlace(@PathVariable("placeId") long placeId,
                                                 @RequestParam(value = "like-count", defaultValue = "false") boolean likeCount,
                                                 @PageableDefault Pageable pageable,
                                                 Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/reviews");

        Page<ReviewDTO> reviewDTOs = reviewService.findAllByPlace(placeId, likeCount, pageable);
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


        Page<ReviewDTO> reviewOverviewDTOs = reviewService.findAllByMedia(mediaId, pageable);
        return ResponseEntity.ok(new Message(reviewOverviewDTOs));
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

    @GetMapping("/api/celebrities/{celebrityId}/reviews")
    public ResponseEntity<Message> findAllReviewByCelebrity(@PathVariable("celebrityId") long celebrityId,
                                                            @PageableDefault Pageable pageable,
                                                            Authentication authentication) throws Exception {

        Page<ReviewDTO> reviewDTOs = reviewService.findAllReviewByCelebrity(celebrityId, pageable);
        return ResponseEntity.ok(new Message(reviewDTOs));
    }
}
