package com.kravel.server.dto.place;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class PlaceDetailDTO {
    private long placeId;

    private String title;
    private String contents;
    private String imageUrl;

    private String location;
    private double latitude;
    private double longitude;
    private String bus;
    private String subway;
    private boolean scrap;
    private List<String> tags;

    private long mediaId;
    private String mediaTitle;

    private int reviewCount;
    private List<CelebrityDTO> celebrities;

    public static PlaceDetailDTO fromEntity(Place entity) {
        PlaceDetailDTO placeDetailDTO = new PlaceDetailDTO();
        placeDetailDTO.setPlaceId(entity.getId());
        placeDetailDTO.setTitle(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getTitle());
        placeDetailDTO.setContents(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getContents());
        placeDetailDTO.setImageUrl(entity.getImageUrl());
        placeDetailDTO.setLocation(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getLocation());
        placeDetailDTO.setLatitude(entity.getLatitude());
        placeDetailDTO.setLongitude(entity.getLongitude());
        placeDetailDTO.setMediaId(Optional.ofNullable(entity
                .getMedia())
                .orElse(new Media()).getId()
        );
        placeDetailDTO.setMediaTitle(Optional.ofNullable(entity.getMedia())
                .orElse(new Media())
                .getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo())
                .getTitle()
        );
        placeDetailDTO.setReviewCount(Optional.ofNullable(entity.getReviews())
                .orElse(new ArrayList<>()).size()
        );
        placeDetailDTO.setCelebrities(Optional.ofNullable(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>()).stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList()))
        ;
        placeDetailDTO.setBus(entity.getBus());
        placeDetailDTO.setSubway(entity.getSubway());
        placeDetailDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>()).stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        placeDetailDTO.setScrap(Optional.ofNullable(entity.getScraps())
                .orElse(new ArrayList<>()).stream()
                .findFirst().isPresent());
        return placeDetailDTO;
    }
}
