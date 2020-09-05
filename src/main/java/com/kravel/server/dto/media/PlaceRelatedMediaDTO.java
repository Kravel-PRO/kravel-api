package com.kravel.server.dto.media;

import com.kravel.server.model.celebrity.CelebrityInfo;
import com.kravel.server.model.place.Place;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class PlaceRelatedMediaDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private List<String> celebrities = new ArrayList<>();

    public static PlaceRelatedMediaDTO fromEntity(Place entity) {
        PlaceRelatedMediaDTO placeRelatedMediaDTO = new PlaceRelatedMediaDTO();
        placeRelatedMediaDTO.setPlaceId(entity.getId());
        placeRelatedMediaDTO.setTitle(entity.getPlaceInfos().stream().filter(info -> info.getSpeech().equals("KOR")).findFirst().get().getTitle());
        placeRelatedMediaDTO.setImageUrl(entity.getImageUrl());
        placeRelatedMediaDTO.setImageUrl(entity.getImageUrl());
        placeRelatedMediaDTO.setCelebrities(Optional.ofNullable(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>()).stream()
                .map(placeCelebrity -> placeCelebrity.getCelebrity().getCelebrityInfos().stream()
                        .findFirst()
                        .orElse(new CelebrityInfo()).getName())
                .collect(Collectors.toList())
        );
        return placeRelatedMediaDTO;
    }
}
