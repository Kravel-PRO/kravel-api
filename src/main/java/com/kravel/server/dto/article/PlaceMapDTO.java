package com.kravel.server.dto.article;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.place.Place;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class PlaceMapDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private long mediaId;
    private String mediaName;

    private List<CelebrityDTO> celebrities;

    public static PlaceMapDTO fromEntity(Place entity) {
        PlaceMapDTO placeMapDTO = new PlaceMapDTO();
        placeMapDTO.setPlaceId(entity.getId());
        placeMapDTO.setTitle(entity.getTitle());
        placeMapDTO.setImageUrl(entity.getImageUrl());
        placeMapDTO.setMediaId(entity.getMedia().getId());
        placeMapDTO.setMediaName(entity.getMedia().getName());
        placeMapDTO.setCelebrities(entity
                .getPlaceCelebrities().stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList()));

        return placeMapDTO;
    }
}
