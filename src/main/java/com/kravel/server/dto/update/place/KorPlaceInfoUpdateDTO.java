package com.kravel.server.dto.update.place;

import com.kravel.server.enums.Speech;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KorPlaceInfoUpdateDTO {
    private Speech speech = Speech.KOR;
    private String title;
    private String location;
    private String tags;
}
