package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.media.MediaArticleDTO;
import com.kravel.server.api.article.dto.media.MediaInfoDTO;
import com.kravel.server.api.article.dto.media.MediaListDTO;
import com.kravel.server.api.article.dto.review.ArticleReviewListDTO;
import com.kravel.server.api.article.service.MediaService;
import com.kravel.server.api.article.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MediaController {

    private final MediaService mediaService;
    private final ClaimExtractor claimExtractor;
    private final ReviewService reviewService;

    @GetMapping("/api/articles/medias")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllMedia(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "max", defaultValue = "5") int max,
                                        @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                        @RequestParam(value = "order", defaultValue = "DESC") String order,
                                        Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        List<MediaListDTO> mediaListDTOList = mediaService.findAllMedia(param);

        return new ResponseMessage(HttpStatus.OK, mediaListDTOList);
    }

    @GetMapping("/api/articles/medias/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaById(@PathVariable("mediaId") long mediaId,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaId);

        MediaInfoDTO result = mediaService.findMediaInfoById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/articles/medias/{mediaId}/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaArticlesById(@PathVariable("mediaId") long mediaID,
                                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "max", defaultValue = "6") int max,
                                                 @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                 @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                 Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaID);
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("langu", claimExtractor.getLangu(authentication));

        List<MediaArticleDTO> result = mediaService.findMediaArticlesById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/articles/medias/{mediaId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaReviewsById(@PathVariable("mediaId") long mediaId,
                                                @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                @RequestParam(value = "max", defaultValue = "6") int max,
                                                @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaId);
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        List<ArticleReviewListDTO> articleReviewListDTOs = reviewService.findAllReviews(param);
        return new ResponseMessage(HttpStatus.OK, articleReviewListDTOs);
    }
}