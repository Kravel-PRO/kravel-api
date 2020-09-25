package com.kravel.server.service;

import com.fasterxml.jackson.core.type.TypeReference;
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
import com.kravel.server.model.mapping.PlaceCelebrity;
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
import java.util.List;
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

        Place place = Place.builder()
                .bus(placeUpdateDTO.getBus())
                .subway(placeUpdateDTO.getSubway())
                .longitude(placeUpdateDTO.getLongitude())
                .latitude(placeUpdateDTO.getLatitude())
                .build();

        KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getKorInfo(), KorPlaceInfoUpdateDTO.class);
        EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getEngInfo(), EngPlaceInfoUpdateDTO.class);
        List<Integer> celebrities = objectMapper.readValue(placeUpdateDTO.getCelebrities(), new TypeReference<List<Integer >>(){});

        List<PlaceInfo> placeInfos = new ArrayList<>();

        PlaceInfo korInfo = new PlaceInfo();
        korInfo.updatePlaceInfo(place, korPlaceInfoUpdateDTO);
        placeInfos.add(korInfo);

        PlaceInfo engInfo = new PlaceInfo();
        engInfo.updatePlaceInfo(place, engPlaceInfoUpdateDTO);
        placeInfos.add(engInfo);

        place.changePlaceInfo(placeInfos);

        List<Tag> tags = new ArrayList<>();

        Tag korTag = new Tag();
        korTag.updateTag(place, korPlaceInfoUpdateDTO);

        Tag engTag = new Tag();
        engTag.updateTag(place, engPlaceInfoUpdateDTO);

        tags.add(korTag);
        tags.add(engTag);

        place.changeTags(tags);

        List<PlaceCelebrity> placeCelebrities = new ArrayList<>();
        if (celebrities.size() != 0) {
            celebrities.forEach(id -> {
                PlaceCelebrity placeCelebrity = new PlaceCelebrity(place, id);
                placeCelebrities.add(placeCelebrity);
            });
            place.changePlaceCelebrities(placeCelebrities);
        }
        if (placeUpdateDTO.getMedia() != 0) {
            long mediaId = placeUpdateDTO.getMedia();
            place.changeMedia(new Media(mediaId));
        }
        if (placeUpdateDTO.getImage() != null) {
            String imageUrl = s3Uploader.upload(placeUpdateDTO.getImage(), "place/represent");
            place.changeImageUrl(imageUrl);
        }
        if (placeUpdateDTO.getSubImage() != null) {
            String subImageUrl = s3Uploader.upload(placeUpdateDTO.getSubImage(), "place/sub");
            place.changeSubImageUrl(subImageUrl);
        }
        if (placeUpdateDTO.getFilterImage() != null) {
            String filterImageUrl = s3Uploader.upload(placeUpdateDTO.getFilterImage(), "place/filter");
            place.changeFilterImageUrl(filterImageUrl);
        }
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
        Media media = mediaRepository.findById(mediaId).orElseThrow(() ->
                new NotFoundException("🔥 error: is not exist media")
        );
        s3Uploader.removeS3Object(media.getImageUrl());
        mediaRepository.delete(media);
    }

    public PlaceWebDTO findUpdateById(long placeId) throws Exception {
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new InvalidRequestException("🔥 error: is not exist place")
        );
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
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new NotFoundException("🔥 error: is not exist place")
        );

        place.changeBus(placeUpdateDTO.getBus());
        place.changeSubway(placeUpdateDTO.getSubway());
        place.changeLatitude(placeUpdateDTO.getLatitude());
        place.changeLongitude(placeUpdateDTO.getLongitude());

        KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getKorInfo(), KorPlaceInfoUpdateDTO.class);
        EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getEngInfo(), EngPlaceInfoUpdateDTO.class);
        List<Integer> celebrities = objectMapper.readValue(placeUpdateDTO.getCelebrities(), new TypeReference<List<Integer >>(){});

        PlaceInfo korInfo = place.getPlaceInfos().stream()
                .filter(placeInfo -> placeInfo.getSpeech().equals(Speech.KOR))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("🔥 error: not found info"));
        korInfo.updatePlaceInfo(place, korPlaceInfoUpdateDTO);

        PlaceInfo engInfo = place.getPlaceInfos().stream()
                .filter(placeInfo -> placeInfo.getSpeech().equals(Speech.ENG))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("🔥 error: not found info"));
        engInfo.updatePlaceInfo(place, engPlaceInfoUpdateDTO);

        Tag korTag = place.getTags().stream()
                .filter(tag -> tag.getSpeech().equals(Speech.KOR))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("🔥 error: not found tag"));
        korTag.updateTag(place, korPlaceInfoUpdateDTO);

        Tag engTag = place.getTags().stream()
                .filter(tag -> tag.getSpeech().equals(Speech.ENG))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("🔥 error: not found tag"));
        engTag.updateTag(place, engPlaceInfoUpdateDTO);

        if (celebrities.size() != 0) {
            if (place.getPlaceCelebrities().size() != 0) {
                place.getPlaceCelebrities().forEach(placeCelebrityRepository::delete);
            }
            List<PlaceCelebrity> placeCelebrities = new ArrayList<>();
            celebrities.forEach(celebrity -> {
                PlaceCelebrity placeCelebrity = new PlaceCelebrity(place, celebrity);
                placeCelebrities.add(placeCelebrity);
            });
            place.changePlaceCelebrities(placeCelebrities);
        }
        if (placeUpdateDTO.getMedia() != 0) {
            if (place.getMedia() != null) {
                mediaRepository.delete(place.getMedia());
            }
            Media savedMedia = new Media(placeUpdateDTO.getMedia());
            mediaRepository.save(savedMedia);
        }
        if (placeUpdateDTO.getImage() != null) {
            if (place.getImageUrl() != null) {
                s3Uploader.removeS3Object(place.getImageUrl());
            }
            String imageUrl = s3Uploader.upload(placeUpdateDTO.getImage(), "place/represent");
            place.changeImageUrl(imageUrl);
        }
        if (placeUpdateDTO.getSubImage() != null) {
            if (place.getSubImageUrl() != null) {
                s3Uploader.removeS3Object(place.getSubImageUrl());
            }
            String subImageUrl = s3Uploader.upload(placeUpdateDTO.getSubImage(), "place/sub");
            place.changeSubImageUrl(subImageUrl);
        }
        if (placeUpdateDTO.getFilterImage() != null) {
            if (place.getFilterImageUrl() != null) {
                s3Uploader.removeS3Object(place.getFilterImageUrl());
            }
            String filterImageUrl = s3Uploader.upload(placeUpdateDTO.getFilterImage(), "place/filter");
            place.changeFilterImageUrl(filterImageUrl);
        }

        placeRepository.save(place);
    }
}
