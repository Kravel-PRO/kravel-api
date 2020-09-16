package com.kravel.server.dto.update;

import com.kravel.server.enums.Speech;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EngPlaceInfoUpdateDTO {
    private Speech speech = Speech.ENG;
    private String title;
    private String location;
    private String tags;
}
