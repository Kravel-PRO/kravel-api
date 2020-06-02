package com.kravel.server.api.article.controller;

import com.auth0.jwt.interfaces.Claim;
import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ClaimExtractor claimExtractor;

    @GetMapping("/{articleId}/reviews")
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

        List<ArticleReviewListDTO> articleReviewListDTOList = reviewService.findAllReviews(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewListDTOList);
    }

    @GetMapping("/{articleId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findReviewDetailById(@PathVariable("articleId") long articleId,
                                                      @PathVariable("reviewId") long reviewId,
                                                      Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("reviewId", reviewId);
        param.put("memberId", claimExtractor.getMemberId(authentication));

        ArticleReviewDTO articleReviewDTO = reviewService.findReviewDetailById(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewDTO);
    }

}
