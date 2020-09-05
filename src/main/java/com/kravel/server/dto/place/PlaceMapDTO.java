package com.kravel.server.dto.place;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.place.Place;
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
public class PlaceMapDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private String subImageUrl;
    private String location;
    private double latitude;
    private double longitude;

    private long reviewCount;
    private List<String> tags = new ArrayList<>();
    private List<CelebrityDTO> celebrities;

    public static PlaceMapDTO fromEntity(Place entity) {
        PlaceMapDTO placeMapDTO = new PlaceMapDTO();
        placeMapDTO.setPlaceId(entity.getId());
        placeMapDTO.setTitle(entity.getPlaceInfos().stream()
                .findFirst().get().getTitle());
        placeMapDTO.setImageUrl(entity.getImageUrl());
        placeMapDTO.setSubImageUrl(entity.getSubImageUrl());
        placeMapDTO.setLocation(entity.getPlaceInfos().stream().findFirst().get().getLocation());
        placeMapDTO.setLongitude(entity.getLongitude());
        placeMapDTO.setLatitude(entity.getLatitude());
        placeMapDTO.setTags(Optional.ofNullable(entity.getTags())
                .orElse(new ArrayList<>())
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
        placeMapDTO.setCelebrities(Optional.of(entity.getPlaceCelebrities())
                .orElse(new ArrayList<>())
                .stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList())
        );
        return placeMapDTO;
    }
}
