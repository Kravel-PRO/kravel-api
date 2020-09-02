package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.place.PhotoFilterDTO;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceMapDTO;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.dto.place.ScrapDTO;
import com.kravel.server.dto.update.PlaceUpdateDTO;
import com.kravel.server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/places")
    public ResponseEntity<Message> findAllByLocation(@RequestParam(value = "latitude", defaultValue = "0") double latitude,
                                                     @RequestParam(value = "longitude", defaultValue = "0") double longitude,
                                                     @RequestParam(value = "height", defaultValue = "0.25") double height,
                                                     @RequestParam(value = "width", defaultValue = "0.25") double width,
                                                     @RequestParam(value = "review-count", defaultValue = "false") boolean reviewCount,
                                                     @PageableDefault Pageable pageable,
                                                     Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places");

        String speech = claimExtractor.getSpeech(authentication);

        Page<PlaceMapDTO> placeMapDTOs = placeService.findAllByLocation(latitude, longitude, height, width, speech, pageable, reviewCount);
        return ResponseEntity.ok().body(new Message(placeMapDTOs));
    }

    @GetMapping("/api/places/{placeId}")
    public ResponseEntity<Message> findPlaceById(@PathVariable("placeId") long placeId,
                                         Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}");

        String speech = claimExtractor.getSpeech(authentication);

        PlaceDTO placeDTO = placeService.findPlaceById(placeId, speech);
        return ResponseEntity.ok().body(new Message(placeDTO));
    }

    @PostMapping("/api/places/{placeId}/scrap")
    public ResponseEntity<Message> handleScrape(@PathVariable("placeId") long placeId,
                                        @RequestBody ScrapDTO scrapDTO,
                                        Authentication authentication) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/scrap");

        long memberId = claimExtractor.getMemberId(authentication);
        long scrapId = placeService.handlePlaceScrap(placeId, memberId, scrapDTO);

        return ResponseEntity.ok().body(new Message(scrapId));
    }

    @GetMapping("/api/places/{placeId}/photo-filter")
    public ResponseEntity<Message> savePlaceInfo(@PathVariable("placeId") long placeId) throws Exception {

        log.info("🎉 GET /api/places/{placeId}/photo-filter");
        PhotoFilterDTO photoFilterDTO = placeService.findPhotoFilterByPlace(placeId);
        return ResponseEntity.ok().body(new Message(photoFilterDTO));

    }

    @PostMapping("/api/places")
    public ResponseEntity<Message> savePlaceInfo(@RequestBody PlaceUpdateDTO placeUpdateDTO) throws Exception {

        log.info("🎉 POST /api/places");

        long placeId = placeService.savePlace(placeUpdateDTO);
        return ResponseEntity.ok().body(new Message(placeId));

    }
}
