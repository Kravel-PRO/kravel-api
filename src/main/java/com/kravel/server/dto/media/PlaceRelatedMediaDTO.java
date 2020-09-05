package com.kravel.server.dto.media;

import com.kravel.server.model.celebrity.CelebrityInfo;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.Tag;
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
    private String tags;

    public static PlaceRelatedMediaDTO fromEntity(Place entity) {
        PlaceRelatedMediaDTO placeRelatedMediaDTO = new PlaceRelatedMediaDTO();
        placeRelatedMediaDTO.setPlaceId(entity.getId());
        placeRelatedMediaDTO.setTitle(entity.getPlaceInfos().stream().findFirst().get().getTitle());
        placeRelatedMediaDTO.setImageUrl(entity.getImageUrl());
        placeRelatedMediaDTO.setImageUrl(entity.getImageUrl());
        placeRelatedMediaDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>()).stream()
                .findFirst()
                .orElse(new Tag()).getName()
        );

        return placeRelatedMediaDTO;
    }
}
