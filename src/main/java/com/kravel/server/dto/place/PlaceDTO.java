package com.kravel.server.dto.place;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceInfo;
import com.kravel.server.model.place.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
@ToString
public class PlaceDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private String subImageUrl;
    private String location;
    private double latitude;
    private double longitude;

    private long reviewCount;
    private String tags;
    private List<CelebrityDTO> celebrities;

    public static PlaceDTO fromEntity(Place entity) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setPlaceId(entity.getId());
        placeDTO.setTitle(entity.getPlaceInfos().stream()
                .findFirst()
                .orElse(new PlaceInfo()).getTitle());
        placeDTO.setImageUrl(entity.getImageUrl());
        placeDTO.setSubImageUrl(entity.getSubImageUrl());
        placeDTO.setLocation(entity.getPlaceInfos().stream()
                .findFirst().orElse(new PlaceInfo())
                .getLocation());
        placeDTO.setLongitude(entity.getLongitude());
        placeDTO.setLatitude(entity.getLatitude());
        placeDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>())
                .stream()
                .findFirst()
                .orElse(new Tag()).getName()
        );
        placeDTO.setCelebrities(Optional.of(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>())
                .stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList()));

        return placeDTO;
    }
}
