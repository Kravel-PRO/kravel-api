package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.api.article.dto.review.ArticleReviewListDTO;
import com.kravel.server.api.article.service.CelebrityService;
import com.kravel.server.api.article.service.ReviewService;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CelebrityController {

    private final CelebrityService celebrityService;
    private final ReviewService reviewService;

    @GetMapping("/api/articles/celebrities")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllCelebrities(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                              @RequestParam(value = "max", defaultValue = "10") int max,
                                              @RequestParam(value = "order", defaultValue = "DESC") String order,
                                              @RequestParam(value = "search", defaultValue = "") String search,
                                              Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("order", order);
        param.put("search", search);

        List<CelebrityDTO> celebrityList = celebrityService.findAllCelebrities(param);

        return new ResponseMessage(HttpStatus.OK, celebrityList);
    }

    @GetMapping("/api/articles/celebrities/{celebrityId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findCelebrityById(@PathVariable("celebrityId") long celebrityId,
                                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                                             @RequestParam(value = "max", defaultValue = "10") int max,
                                             @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                             @RequestParam(value = "order", defaultValue = "DESC") String order,
                                             Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("celebrityId", celebrityId);
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        CelebrityDetailDTO result = celebrityService.findCelebrityById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/articles/celebrities/{celebrityId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllCelebrityReview(@PathVariable("celebrityId") long celebrityId,
                                                  @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                  @RequestParam(value = "max", defaultValue = "6") int max,
                                                  @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                  @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                  Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("celebrityId", celebrityId);

        List<ArticleReviewListDTO> result = reviewService.findAllCelebrityReviews(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

}
