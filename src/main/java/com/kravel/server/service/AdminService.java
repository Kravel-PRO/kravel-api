package com.kravel.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.dto.update.media.MediaUpdateDTO;
import com.kravel.server.dto.update.place.PlaceUpdateDTO;
import com.kravel.server.model.celebrity.CelebrityRepository;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ObjectMapper objectMapper;
    private final S3Uploader s3Uploader;

    private final PlaceRepository placeRepository;
    private final MediaRepository mediaRepository;
    private final CelebrityRepository celebrityRepository;

    public long savePlace(PlaceUpdateDTO placeUpdateDTO) throws Exception {
        Place place = new Place(placeUpdateDTO, s3Uploader, objectMapper);
        return placeRepository.save(place).getId();
    }

    public void deletePlace(long placeId) throws Exception {
        placeRepository.deleteById(placeId);
    }

    public long saveMedia(MediaUpdateDTO mediaUpdateDTO) throws Exception {
        Media media = new Media(mediaUpdateDTO, s3Uploader, objectMapper);
        return mediaRepository.save(media).getId();
    }

    public void deleteMedia(long mediaId) throws Exception {
        mediaRepository.deleteById(mediaId);
    }
}
