package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PlaceInfo {
    private long infoId;
    private long placeId;

    private String title;
    private String contents;
    private String speech;
}
