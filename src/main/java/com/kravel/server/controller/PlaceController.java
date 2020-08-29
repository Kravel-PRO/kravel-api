package com.kravel.server.controller;

import com.kravel.server.dto.article.PlaceDTO;
import com.kravel.server.dto.article.PlaceMapDTO;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import com.kravel.server.dto.article.ScrapDTO;
import com.kravel.server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllPlacesByLocation(@RequestParam(value = "latitude", required = true) double latitude,
                                                   @RequestParam(value = "longitude", required = true) double longitude,
                                                   @RequestParam(value = "height", defaultValue = "0.25") double height,
                                                   @RequestParam(value = "width", defaultValue = "0.25") double width,
                                                   @PageableDefault Pageable pageable,
                                                   Authentication authentication) throws Exception {


        String speech = claimExtractor.getSpeech(authentication);

        List<PlaceMapDTO> placeMapDTOList = placeService.findAllPlacesByLocation(latitude, longitude, height, width, speech, pageable);
        return new ResponseMessage(HttpStatus.OK, placeMapDTOList);
    }

    @GetMapping("/api/places/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findPlaceById(@PathVariable("placeId") long placeId,
                                         Authentication authentication) throws Exception {

        String speech = claimExtractor.getSpeech(authentication);

        PlaceDTO placeDTO = placeService.findPlaceById(placeId, speech);
        return new ResponseMessage(HttpStatus.OK, placeDTO);
    }

    @PostMapping("/api/places/{placeId}/scrap")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage handleScrape(@PathVariable("placeId") long placeId,
                                        @RequestBody ScrapDTO scrapDTO,
                                        Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        long scrapId = placeService.handlePlaceScrap(placeId, memberId, scrapDTO);

        return new ResponseMessage(HttpStatus.OK, scrapId);
    }

}
