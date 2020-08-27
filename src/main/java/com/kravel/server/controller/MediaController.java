package com.kravel.server.controller;

import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.service.MediaService;
import com.kravel.server.service.ReviewService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/api/medias")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllMedia(@PageableDefault Pageable pageable,
                                        Authentication authentication) throws Exception {

        List<MediaOverviewDTO> mediaListDTO = mediaService.findAllMedia(pageable);
        return new ResponseMessage(HttpStatus.OK, mediaListDTO);
    }

    @GetMapping("/api/medias/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaById(@PathVariable("mediaId") long mediaId,
                                         Authentication authentication) throws Exception {

        MediaDTO result = mediaService.findMediaInfoById(mediaId);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/medias/{mediaId}/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaArticlesById(@PathVariable("mediaId") long mediaID,
                                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "size", defaultValue = "6") int size,
                                                 @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                 @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                 Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaID);
        param.put("offset", offset);
        param.put("size", size);
        param.put("sort", sort);
        param.put("order", order);
        param.put("speech", claimExtractor.getSpeech(authentication));

        List<PlaceRelatedMediaDTO> result = mediaService.findAllPlaceByMedia(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/medias/{mediaId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaReviewsById(@PathVariable("mediaId") long mediaId,
                                                @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                @RequestParam(value = "size", defaultValue = "6") int size,
                                                @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaId);
        param.put("offset", offset);
        param.put("size", size);
        param.put("sort", sort);
        param.put("order", order);

        List<ReviewOverviewDTO> reviewOverviewDTOS = reviewService.findAllReviews(param);
        return new ResponseMessage(HttpStatus.OK, reviewOverviewDTOS);
    }
}