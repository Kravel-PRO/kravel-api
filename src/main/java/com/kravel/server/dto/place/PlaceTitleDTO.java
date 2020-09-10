package com.kravel.server.dto.place;

import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Optional;

@Getter @Setter
public class PlaceTitleDTO {
    private long placeId;
    private String title;
    private String tags;

    public static PlaceTitleDTO fromEntity(Place entity) {
        PlaceTitleDTO placeTitleDTO = new PlaceTitleDTO();
        placeTitleDTO.setPlaceId(entity.getId());
        placeTitleDTO.setTitle(entity.getPlaceInfos().stream().findFirst().orElse(new PlaceInfo()).getTitle());
        placeTitleDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>())
                .stream()
                .findFirst().orElse(new Tag())
                .getName()
        );
        return placeTitleDTO;
    }
}
