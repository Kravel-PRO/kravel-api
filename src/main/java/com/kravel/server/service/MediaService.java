package com.kravel.server.service;

import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.mapper.CelebrityMapper;
import com.kravel.server.mapper.MediaMapper;
import com.kravel.server.mapper.PlaceMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.media.Media;
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
    private final PlaceQueryRepository placeQueryRepository;

    public List<MediaOverviewDTO> findAllMedia(Pageable pageable) throws Exception {
        Page<Media> mediaPage = mediaRepository.findAll(pageable);
        return mediaPage.stream().map(MediaOverviewDTO::fromEntity).collect(Collectors.toList());
    }

    public MediaDTO findMediaInfoById(long mediaId) throws Exception {

        Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
        if (optionalMedia.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist media information");
        }

        return MediaDTO.fromEntity(optionalMedia.get());
    }

    public List<PlaceRelatedMediaDTO> findAllPlaceByMedia(long mediaId, String speech) throws Exception {

        Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
        if (optionalMedia.isEmpty()) {
            throw new NotFoundException("🔥 error: iS not exist media");
        }

        List<Place> places = placeQueryRepository.findAllByMedia(mediaId);
        List<PlaceRelatedMediaDTO> placeRelatedMediaDTOs = places.stream().map(PlaceRelatedMediaDTO::fromEntity).collect(Collectors.toList());

        return placeRelatedMediaDTOs;
    }
}
