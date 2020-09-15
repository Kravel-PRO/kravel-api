package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KorPlaceInfoUpdateDTO {
    private String speech = "KOR";
    private String title;
    private String location;
    private String tags;
}
