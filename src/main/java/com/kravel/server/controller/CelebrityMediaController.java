package com.kravel.server.controller;

import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.LogHandler;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.SearchDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.service.CelebrityMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CelebrityMediaController {

    private final ClaimExtractor claimExtractor;
    private final CelebrityMediaService celebrityMediaService;

    @GetMapping("/api/search")
    public ResponseEntity<Message> findBySearch(@RequestParam(value = "search") String search,
                                                @PageableDefault Pageable pageable,
                                                Authentication authentication,
                                                HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        Speech speech = claimExtractor.getSpeech(authentication);
        SearchDTO searchDTOs = celebrityMediaService.findBySearch(search, speech, pageable);

        return ResponseEntity.ok(new Message(searchDTOs));
    }
}
