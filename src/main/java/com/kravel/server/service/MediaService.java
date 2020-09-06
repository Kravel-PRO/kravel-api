package com.kravel.server.service;

import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaQueryRepository;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaQueryRepository mediaQueryRepository;

    public List<MediaOverviewDTO> findAll(Pageable pageable) throws Exception {
        Page<Media> mediaPage = mediaRepository.findAll(pageable);
        return mediaPage.stream().map(MediaOverviewDTO::fromEntity).collect(Collectors.toList());
    }

    public MediaDTO findById(long mediaId, String speech) throws Exception {

        Media media = mediaQueryRepository.findById(mediaId, speech)
                .orElseThrow(() -> new InvalidRequestException("🔥 error: is not exist media information"));

        return MediaDTO.fromEntity(media);
    }
}
