package com.kravel.server.api.controller;

import com.kravel.server.api.dto.review.ImgDTO;
import com.kravel.server.api.dto.review.ReviewDTO;
import com.kravel.server.api.dto.review.ArticleReviewListDTO;
import com.kravel.server.api.dto.review.ReviewLikeDTO;
import com.kravel.server.api.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/articles/{articleId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllReviews(@PathVariable("articleId") long articleId,
                                          @RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "max", defaultValue = "5") int max,
                                          @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                          @RequestParam(value = "order", defaultValue = "desc") String order,
                                          Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("articleId", articleId);

        List<ArticleReviewListDTO> articleReviewListDTOs = reviewService.findAllReviews(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewListDTOs);
    }

    @GetMapping("/api/articles/{articleId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findReviewDetailById(@PathVariable("articleId") long articleId,
                                                @PathVariable("reviewId") long reviewId,
                                                Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("reviewId", reviewId);
        param.put("memberId", claimExtractor.getMemberId(authentication));

        ReviewDTO articleReviewDTO = reviewService.findReviewDetailById(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewDTO);
    }

    @PostMapping("/api/articles/{articleId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    public ResponseMessage saveReview(@PathVariable("articleId") int articleId,
                                      @RequestParam("files") List<MultipartFile> files,
                                      @RequestParam(value = "represent", defaultValue = "0") int represent,
                                      Authentication authentication) throws Exception {

        List<String> imgUrls = reviewService.saveReviewToS3(files);

        List<ImgDTO> imgDTOs = new ArrayList<ImgDTO>();

        for (int i=0; i<imgUrls.size(); i++) {
            ImgDTO imgDTO = new ImgDTO();
            imgDTO.setImgUrl(imgUrls.get(i));

            if (i == represent) {
                imgDTO.setRepresent(true);
            } else {
                imgDTO.setRepresent(false);
            }
            imgDTOs.add(imgDTO);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("memberId", claimExtractor.getMemberId(authentication));
        param.put("imgDTOs", imgDTOs);
        param.put("represent", represent);

        boolean result = reviewService.saveReviewToDatabase(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @PostMapping("/api/articles/{articleId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage handleReviewLike(@PathVariable("articleId") long articleId,
                                            @PathVariable("reviewId") long reviewId,
                                            @RequestBody ReviewLikeDTO reviewLikeDTO,
                                            Authentication authentication) throws Exception{

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("reviewId", reviewId);
        param.put("likeState", reviewLikeDTO.isLikeState());
        param.put("memberId", claimExtractor.getMemberId(authentication));

        boolean result = reviewService.handleReviewLike(param);

        return new ResponseMessage(HttpStatus.OK, result);

    }

}
