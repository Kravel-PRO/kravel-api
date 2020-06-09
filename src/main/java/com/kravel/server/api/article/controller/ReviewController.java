package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.dto.ReviewLikeDTO;
import com.kravel.server.api.article.dto.RwImgDTO;
import com.kravel.server.api.article.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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

    @PostMapping("/{articleId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    public ResponseMessage saveReview(@PathVariable("articleId") int articleId,
                                      @RequestParam("files") List<MultipartFile> files,
                                      @RequestParam(value = "represent", defaultValue = "0") int represent,
                                      Authentication authentication) throws Exception {

        List<String> imgUrlList = reviewService.saveReviewToS3(files);

        List<RwImgDTO> rwImgDTOList = new ArrayList<RwImgDTO>();

        for (int i=0; i<imgUrlList.size(); i++) {
            RwImgDTO rwImgDTO = new RwImgDTO();
            rwImgDTO.setImgUrl(imgUrlList.get(i));

            if (i == represent) {
                rwImgDTO.setRepresent(true);
            } else {
                rwImgDTO.setRepresent(false);
            }
            rwImgDTOList.add(rwImgDTO);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("memberId", claimExtractor.getMemberId(authentication));
        param.put("rwImgDTOList", rwImgDTOList);
        param.put("represent", represent);

        boolean result = reviewService.saveReviewToDatabase(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @PostMapping("/{articleId}/reviews/{reviewId}")
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