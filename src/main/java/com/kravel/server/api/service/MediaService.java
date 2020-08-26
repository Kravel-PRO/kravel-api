package com.kravel.server.api.service;

import com.kravel.server.api.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.api.dto.media.MediaDTO;
import com.kravel.server.api.dto.media.MediaOverviewDTO;
import com.kravel.server.api.dto.media.RawPlaceRelatedMediaDTO;
import com.kravel.server.api.mapper.MediaMapper;
import com.kravel.server.api.mapper.PlaceMapper;
import com.kravel.server.api.model.Media;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final MediaMapper mediaMapper;
    private final PlaceMapper placeMapper;

    public List<MediaOverviewDTO> findAllMedia(Map<String, Object> param) throws Exception {
        List<Media> medias = mediaMapper.findAllMedia(param);
        return medias.stream().map(MediaOverviewDTO::fromEntity).collect(Collectors.toList());
    }

    public MediaDTO findMediaInfoById(Map<String, Object> param) throws Exception {

        Media media = mediaMapper.findMediaById(param);
        if (media.getName().isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist media information");
        }

        return MediaDTO.fromEntity(media);
    }

    public List<PlaceRelatedMediaDTO> findMediaArticlesById(Map<String, Object> param) throws Exception {

        List<RawPlaceRelatedMediaDTO> rawPlaceRelatedMediaDTOs = placeMapper.findAllPlaceByMedia(param);
        if (rawPlaceRelatedMediaDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: iS not exist media articles");
        }

        List<PlaceRelatedMediaDTO> placeRelatedMediaDTOs = new ArrayList<>();
        for (RawPlaceRelatedMediaDTO rawPlaceRelatedMediaDTO : rawPlaceRelatedMediaDTOs) {
            placeRelatedMediaDTOs.add(new PlaceRelatedMediaDTO(rawPlaceRelatedMediaDTO));
        }

        return placeRelatedMediaDTOs;
    }
}
