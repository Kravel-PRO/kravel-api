package com.kravel.server.service;

import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.dto.media.MediaDetailDTO;
import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaQueryRepository;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaQueryRepository mediaQueryRepository;

    @Transactional(readOnly = true)
    public Page<MediaOverviewDTO> findAll(Pageable pageable, Speech speech) throws Exception {
        Page<Media> medias = mediaQueryRepository.findAll(pageable, speech);
        return medias.map(new Function<Media, MediaOverviewDTO>() {
            @Override
            public MediaOverviewDTO apply(Media media) {
                return MediaOverviewDTO.fromEntity(media);
            }
        });
    }

    @Transactional(readOnly = true)
    public MediaDetailDTO findById(long mediaId, Speech speech, Pageable pageable) throws Exception {

        Media media = mediaQueryRepository.findById(mediaId, speech)
                .orElseThrow(() -> new NotFoundException("🔥 error: is not exist media information"));
        List<Place> places = mediaQueryRepository.findAllByMedia(mediaId, speech, pageable);
        List<PlaceDTO> placeDTOs = places.stream().map(PlaceDTO::fromEntity).collect(Collectors.toList());;

        MediaDTO mediaDTO = MediaDTO.fromEntity(media);

        return MediaDetailDTO.builder()
                .media(mediaDTO)
                .places(placeDTOs)
                .build();
    }
}
