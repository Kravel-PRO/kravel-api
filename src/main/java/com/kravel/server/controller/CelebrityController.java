package com.kravel.server.controller;

import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.dto.review.ReviewDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.service.CelebrityService;
import com.kravel.server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CelebrityController {

    private final CelebrityService celebrityService;
    private final ReviewService reviewService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/api/celebrities")
    public ResponseEntity<Message> findAllCelebrities(@RequestParam(value = "search", defaultValue = "") String search,
                                      @PageableDefault Pageable pageable,
                                      Authentication authentication) throws Exception {

        List<CelebrityDTO> celebrities = celebrityService.findAllCelebrities(search, pageable);
        return ResponseEntity.ok(new Message(celebrities));
    }

    @GetMapping("/api/celebrities/{celebrityId}")
    public ResponseEntity<Message> findCelebrityById(@PathVariable("celebrityId") long celebrityId,
                                            Authentication authentication) throws Exception {

        String speech = claimExtractor.getSpeech(authentication);
        CelebrityDetailDTO result = celebrityService.findCelebrityById(celebrityId, speech);
        return ResponseEntity.ok(new Message(result));
    }

}
