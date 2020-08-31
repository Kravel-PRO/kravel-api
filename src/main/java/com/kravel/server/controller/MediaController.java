package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.service.MediaService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MediaController {

    private final MediaService mediaService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/medias")
    public ResponseEntity<Message> findAllMedia(@PageableDefault Pageable pageable,
                                                Authentication authentication) throws Exception {

        List<MediaOverviewDTO> mediaListDTO = mediaService.findAllMedia(pageable);
        return ResponseEntity.ok(new Message(mediaListDTO));
    }

    @GetMapping("/api/medias/{mediaId}")
    public ResponseEntity<Message> findMediaById(@PathVariable("mediaId") long mediaId,
                                         Authentication authentication) throws Exception {

        MediaDTO result = mediaService.findMediaInfoById(mediaId);
        return ResponseEntity.ok(new Message(result));
    }

    @GetMapping("/api/medias/{mediaId}/places")
    public ResponseEntity<Message> findAllPlaceByMedia(@PathVariable("mediaId") long mediaId,
                                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "size", defaultValue = "6") int size,
                                                 @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                 @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                 Authentication authentication) throws Exception {

        String speech = claimExtractor.getSpeech(authentication);
        List<PlaceRelatedMediaDTO> result = mediaService.findAllPlaceByMedia(mediaId, speech);
        return ResponseEntity.ok(new Message(result));
    }

    // TODO: review controller로 옮겨야한다.
//    @GetMapping("/api/medias/{mediaId}/reviews")
//    public ResponseEntity<Message> findAllReviewsByMedia(@PathVariable("mediaId") long mediaId,
//                                                @PageableDefault Pageable pageable,
//                                                Authentication authentication) throws Exception {
//
//
//        List<ReviewOverviewDTO> reviewOverviewDTOs = reviewService.findAllReviews(param);
//        return ResponseEntity.ok(new Message(reviewOverviewDTOs));
//    }
}