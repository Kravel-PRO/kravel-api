package com.kravel.server.controller;

import com.kravel.server.dto.article.PlaceDTO;
import com.kravel.server.dto.article.PlaceMapDTO;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import com.kravel.server.dto.article.ScrapDTO;
import com.kravel.server.dto.update.PlaceUpdateDTO;
import com.kravel.server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllByLocation(@RequestParam(value = "latitude", defaultValue = "0") double latitude,
                                             @RequestParam(value = "longitude", defaultValue = "0") double longitude,
                                             @RequestParam(value = "height", defaultValue = "0.25") double height,
                                             @RequestParam(value = "width", defaultValue = "0.25") double width,
                                             @RequestParam(value = "review-count", defaultValue = "false") boolean reviewCount,
                                             @PageableDefault Pageable pageable,
                                             Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places");

        String speech = claimExtractor.getSpeech(authentication);

        Page<PlaceMapDTO> placeMapDTOs = placeService.findAllByLocation(latitude, longitude, height, width, speech, pageable, reviewCount);
        return new ResponseMessage(placeMapDTOs);
    }

    @GetMapping("/api/places/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findPlaceById(@PathVariable("placeId") long placeId,
                                         Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}");

        String speech = claimExtractor.getSpeech(authentication);

        PlaceDTO placeDTO = placeService.findPlaceById(placeId, speech);
        return new ResponseMessage(placeDTO);
    }

    @PostMapping("/api/places/{placeId}/scrap")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage handleScrape(@PathVariable("placeId") long placeId,
                                        @RequestBody ScrapDTO scrapDTO,
                                        Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/scrap");

        long memberId = claimExtractor.getMemberId(authentication);
        long scrapId = placeService.handlePlaceScrap(placeId, memberId, scrapDTO);

        return new ResponseMessage(scrapId);
    }

    @PostMapping("/api/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage savePlaceInfo(@RequestBody PlaceUpdateDTO placeUpdateDTO) throws Exception {

        log.info("🎉 POST /api/places");

        long placeId = placeService.savePlace(placeUpdateDTO);
        return new ResponseMessage(placeId);

    }
}
