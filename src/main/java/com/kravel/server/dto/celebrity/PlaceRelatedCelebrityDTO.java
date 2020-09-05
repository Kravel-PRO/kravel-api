package com.kravel.server.dto.celebrity;

import com.kravel.server.model.media.Media;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.place.Place;
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
    private String mediaName;
    private List<String> celebrities;

    public static PlaceRelatedCelebrityDTO fromEntity(Place entity) {
        PlaceRelatedCelebrityDTO placeRelatedCelebrityDTO = new PlaceRelatedCelebrityDTO();
        placeRelatedCelebrityDTO.setPlaceId(entity.getId());
        placeRelatedCelebrityDTO.setTitle(entity.getPlaceInfos().stream().filter(info -> info.getSpeech().equals("KOR")).findFirst().get().getTitle());
        placeRelatedCelebrityDTO.setImageUrl(entity.getImageUrl());
        placeRelatedCelebrityDTO.setCelebrities(Optional.ofNullable(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>())
                .stream()
                .map(placeCelebrity -> placeCelebrity.getCelebrity().getName())
                .collect(Collectors.toList())
        );
        placeRelatedCelebrityDTO.setMediaName(Optional.ofNullable(entity.getMedia())
                .orElse(new Media())
                .getName()
        );
        return placeRelatedCelebrityDTO;
    }
}
