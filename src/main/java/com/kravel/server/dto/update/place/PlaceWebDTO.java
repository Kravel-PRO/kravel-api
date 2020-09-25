package com.kravel.server.dto.update.place;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class PlaceWebDTO {

    private long placeId;
    private String bus;
    private String subway;
    private double latitude;
    private double longitude;
    private List<Long> celebrities = new ArrayList<>();
    private long media;

    private KorPlaceInfoUpdateDTO korInfo;
    private EngPlaceInfoUpdateDTO engInfo;
}
