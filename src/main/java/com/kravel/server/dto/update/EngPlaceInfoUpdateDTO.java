package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EngPlaceInfoUpdateDTO {
    private String speech = "ENG";
    private String title;
    private String location;
    private String tags;
}
