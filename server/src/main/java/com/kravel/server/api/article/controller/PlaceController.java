package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.dto.ArticleDetailDTO;
import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.service.PlaceService;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
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
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ClaimExtractor claimExtractor;

    @GetMapping("/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllPlaces(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "max", defaultValue = "5") int max,
                                         @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                         @RequestParam(value = "order", defaultValue = "desc") String order,
                                         @RequestParam(value = "search", defaultValue = "") String search,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("search", search);
        param.put("langu", claimExtractor.getLangu(authentication));

        PostAuthorizationToken postAuthorizationToken = (PostAuthorizationToken) authentication;

        List<Article> articleList = placeService.findAllPlaces(param);
        return new ResponseMessage(HttpStatus.OK, articleList);
    }

    @GetMapping("/places/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findPlaceByArticleId(@PathVariable("articleId") long articleId,
                                                Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("langu", claimExtractor.getLangu(authentication));

        ArticleDetailDTO articleDetailDTO = placeService.findPlaceByArticleId(param);
        return new ResponseMessage(HttpStatus.OK, articleDetailDTO);
    }

    @GetMapping("/places/{articleId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findReviewListByArticleId(@PathVariable("articleId") long articleId,
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

        List<ArticleReviewDTO> articleReviewDTOList = placeService.findReviewListByArticleId(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewDTOList);
    }

    @GetMapping("/places/{articleId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findReviewDetailByReviewId(@PathVariable("articleId") long articleId,
                                                      @PathVariable("reviewId") long reviewId,
                                                      Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("reviewId", reviewId);
        param.put("memberId", claimExtractor.getMemberId(authentication));

        ArticleReviewDTO articleReviewDTO = placeService.findReviewDetailByReviewId(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewDTO);
    }


}
