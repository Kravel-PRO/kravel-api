package com.kravel.server.controller;

import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.media.MediaDetailDTO;
import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.service.MediaService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Message> findAll(@PageableDefault Pageable pageable,
                                                Authentication authentication) throws Exception {

        Page<MediaOverviewDTO> mediaOverviewDTOs = mediaService.findAll(pageable);
        return ResponseEntity.ok(new Message(mediaOverviewDTOs));
    }

    @GetMapping("/api/medias/{mediaId}")
    public ResponseEntity<Message> findById(@PathVariable("mediaId") long mediaId,
                                            @PageableDefault Pageable pageable,
                                            Authentication authentication) throws Exception {

        Speech speech = claimExtractor.getSpeech(authentication);

        MediaDetailDTO mediaDTO = mediaService.findById(mediaId, speech, pageable);
        return ResponseEntity.ok(new Message(mediaDTO));
    }
}