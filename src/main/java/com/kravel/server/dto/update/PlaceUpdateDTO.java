package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PlaceUpdateDTO {

    private String location;
    private String bus;
    private String subway;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private String subImageUrl;
    private String filterImageUrl;

    private List<PlaceInfoUpdateDTO> placeInfos;
    private List<TagDTO> tags;
}
