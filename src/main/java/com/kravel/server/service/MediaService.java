package com.kravel.server.service;

import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.mapper.CelebrityMapper;
import com.kravel.server.mapper.MediaMapper;
import com.kravel.server.mapper.PlaceMapper;
import com.kravel.server.api.model.Celebrity;
import com.kravel.server.api.model.Media;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final MediaMapper mediaMapper;
    private final PlaceMapper placeMapper;
    private final CelebrityMapper celebrityMapper;

    public List<MediaOverviewDTO> findAllMedia(Map<String, Object> param) throws Exception {
        List<Media> medias = mediaMapper.findAllMedia(param);
        return medias.stream().map(MediaOverviewDTO::fromEntity).collect(Collectors.toList());
    }

    public MediaDTO findMediaInfoById(Map<String, Object> param) throws Exception {

        Media media = mediaMapper.findMediaById(param);
        if (media.getMediaName().isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist media information");
        }

        return MediaDTO.fromEntity(media);
    }

    public List<PlaceRelatedMediaDTO> findAllPlaceByMedia(Map<String, Object> param) throws Exception {

        List<PlaceRelatedMediaDTO> placeRelatedMediaDTOs = placeMapper.findAllPlaceAndPlaceInfoByMedia(param);
        if (placeRelatedMediaDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: iS not exist media articles");
        }

        for (PlaceRelatedMediaDTO placeRelatedMediaDTO: placeRelatedMediaDTOs) {
            placeRelatedMediaDTO.setCelebrities(celebrityMapper
                    .findAllCelebrityByPlace(placeRelatedMediaDTO.getPlaceId()).stream()
                    .map(Celebrity::getCelebrityName)
                    .collect(Collectors.toList()));
        }

        return placeRelatedMediaDTOs;
    }
}
