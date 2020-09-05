package com.kravel.server.dto.celebrity;

import com.kravel.server.model.celebrity.CelebrityInfo;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class PlaceRelatedCelebrityDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private String mediaTitle;
    private List<String> celebrities;

    public static PlaceRelatedCelebrityDTO fromEntity(Place entity) {
        PlaceRelatedCelebrityDTO placeRelatedCelebrityDTO = new PlaceRelatedCelebrityDTO();
        placeRelatedCelebrityDTO.setPlaceId(entity.getId());
        placeRelatedCelebrityDTO.setTitle(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getTitle());
        placeRelatedCelebrityDTO.setImageUrl(entity.getImageUrl());
        placeRelatedCelebrityDTO.setCelebrities(entity.getPlaceCelebrities().stream()
                .map(placeCelebrity -> placeCelebrity.getCelebrity().getCelebrityInfos().stream()
                        .findFirst()
                        .orElse(new CelebrityInfo()).getName())
                .collect(Collectors.toList())
        );
        placeRelatedCelebrityDTO.setMediaTitle(Optional.ofNullable(entity.getMedia())
                .orElse(new Media())
                .getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getTitle()
        );
        return placeRelatedCelebrityDTO;
    }
}
