package com.kravel.server.dto.place;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.Tag;
import com.kravel.server.model.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class PlaceDTO {
    private long placeId;

    private String title;
    private String contents;
    private String imageUrl;

    private String location;
    private double latitude;
    private double longitude;
    private String bus;
    private String subway;
    private List<String> tags;

    private long mediaId;
    private String mediaTitle;

    private int reviewCount;
    private List<CelebrityDTO> celebrities;

    public static PlaceDTO fromEntity(Place entity) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setPlaceId(entity.getId());
        placeDTO.setTitle(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getTitle());
        placeDTO.setContents(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getContents());
        placeDTO.setImageUrl(entity.getImageUrl());
        placeDTO.setLocation(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getLocation());
        placeDTO.setLatitude(entity.getLatitude());
        placeDTO.setLongitude(entity.getLongitude());
        placeDTO.setMediaId(Optional.ofNullable(entity
                .getMedia())
                .orElse(new Media()).getId()
        );
        placeDTO.setMediaTitle(Optional.ofNullable(entity.getMedia())
                .orElse(new Media())
                .getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo())
                .getTitle()
        );
        placeDTO.setReviewCount(Optional.ofNullable(entity.getReviews())
                .orElse(new ArrayList<>()).size()
        );
        placeDTO.setCelebrities(Optional.ofNullable(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>()).stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList()))
        ;
        placeDTO.setBus(entity.getBus());
        placeDTO.setSubway(entity.getSubway());
        placeDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>()).stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        return placeDTO;
    }
}
