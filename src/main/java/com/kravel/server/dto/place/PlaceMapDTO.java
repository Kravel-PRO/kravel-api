package com.kravel.server.dto.place;

import com.kravel.server.model.place.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceMapDTO {

    private long placeId;
    private double latitude;
    private double longitude;

    public static PlaceMapDTO fromEntity(Place place) {
        PlaceMapDTO placeMapDTO = new PlaceMapDTO();
        placeMapDTO.setPlaceId(place.getId());
        placeMapDTO.setLatitude(place.getLatitude());
        placeMapDTO.setLongitude(place.getLongitude());

        return placeMapDTO;
    }
}
