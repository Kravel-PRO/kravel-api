package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PlaceUpdateDTO {

    private String location;
    private String bus;
    private String subway;
    private String latitude;
    private String longitude;
    private String imageUrl;

    List<PlaceInfoUpdateDTO> placeInfos;
}
