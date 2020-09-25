package com.kravel.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.update.media.MediaUpdateDTO;
import com.kravel.server.dto.update.place.EngPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.place.KorPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.place.PlaceUpdateDTO;
import com.kravel.server.dto.update.place.PlaceWebDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.celebrity.CelebrityRepository;
import com.kravel.server.model.mapping.PlaceCelebrityRepository;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.PlaceRepository;
import com.kravel.server.model.place.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ObjectMapper objectMapper;
    private final S3Uploader s3Uploader;

    private final PlaceRepository placeRepository;
    private final MediaRepository mediaRepository;
    private final PlaceCelebrityRepository placeCelebrityRepository;
    private final CelebrityRepository celebrityRepository;

    public long savePlace(PlaceUpdateDTO placeUpdateDTO) throws Exception {
        Place place = new Place();
        place.fromPlaceUpdateDTO(placeUpdateDTO, s3Uploader, objectMapper, placeCelebrityRepository, mediaRepository);
        return placeRepository.save(place).getId();
    }

    public void deletePlace(long placeId) throws Exception {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist place"));
        if (place.getImageUrl() != null) {
            s3Uploader.removeS3Object(place.getImageUrl());
        }
        if (place.getSubImageUrl() != null) {
            s3Uploader.removeS3Object(place.getSubImageUrl());
        }
        if (place.getFilterImageUrl() != null) {
            s3Uploader.removeS3Object(place.getFilterImageUrl());
        }

        placeRepository.delete(place);
    }

    public long saveMedia(MediaUpdateDTO mediaUpdateDTO) throws Exception {
        Media media = new Media(mediaUpdateDTO, s3Uploader, objectMapper);
        return mediaRepository.save(media).getId();
    }

    public void deleteMedia(long mediaId) throws Exception {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist media"));
        s3Uploader.removeS3Object(media.getImageUrl());
        mediaRepository.delete(media);
    }

    public PlaceWebDTO findUpdateById(long placeId) throws Exception {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new InvalidRequestException("🔥 error: is not exist place"));
        PlaceWebDTO placeWebDTO = new PlaceWebDTO();
        placeWebDTO.setBus(place.getBus());
        placeWebDTO.setSubway(place.getSubway());
        placeWebDTO.setLatitude(place.getLatitude());
        placeWebDTO.setLongitude(place.getLongitude());

        KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO = new KorPlaceInfoUpdateDTO();
        PlaceInfo korInfo = place.getPlaceInfos().stream().filter(placeInfo -> placeInfo.getSpeech().equals(Speech.KOR)).findFirst().orElse(new PlaceInfo());
        Tag korTag = place.getTags().stream().filter(tag -> tag.getSpeech().equals(Speech.KOR)).findFirst().orElseThrow(() -> new InvalidRequestException("🔥 error: is not exist tag"));
        korPlaceInfoUpdateDTO.setLocation(korInfo.getLocation());
        korPlaceInfoUpdateDTO.setTitle(korInfo.getTitle());
        korPlaceInfoUpdateDTO.setTags(korTag.getName());
        placeWebDTO.setKorInfo(korPlaceInfoUpdateDTO);

        EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO = new EngPlaceInfoUpdateDTO();
        PlaceInfo engInfo = place.getPlaceInfos().stream().filter(placeInfo -> placeInfo.getSpeech().equals(Speech.ENG)).findFirst().orElse(new PlaceInfo());
        Tag engTag = place.getTags().stream().filter(tag -> tag.getSpeech().equals(Speech.ENG)).findFirst().orElseThrow(() -> new InvalidRequestException("🔥 error: is not exist tag"));
        engPlaceInfoUpdateDTO.setLocation(engInfo.getLocation());
        engPlaceInfoUpdateDTO.setTitle(engInfo.getTitle());
        engPlaceInfoUpdateDTO.setTags(engTag.getName());
        placeWebDTO.setEngInfo(engPlaceInfoUpdateDTO);

        placeWebDTO.setPlaceId(place.getId());

        placeWebDTO.setMedia(Optional.ofNullable(place.getMedia()).orElse(new Media()).getId());
        placeWebDTO.setCelebrities(Optional.ofNullable(place.getPlaceCelebrities()).orElse(new ArrayList<>()).stream().map(placeCelebrity -> placeCelebrity.getCelebrity().getId()).collect(Collectors.toList()));


        return placeWebDTO;
    }


    public void updatePlace(PlaceUpdateDTO placeUpdateDTO, long placeId) throws Exception {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist place"));
        place.fromPlaceUpdateDTO(placeUpdateDTO, s3Uploader, objectMapper, placeCelebrityRepository, mediaRepository);
        placeRepository.save(place);
    }
}
