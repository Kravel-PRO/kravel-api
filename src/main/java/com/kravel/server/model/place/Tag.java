package com.kravel.server.model.place;

import com.kravel.server.dto.update.EngPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.KorPlaceInfoUpdateDTO;
import com.kravel.server.enums.Speech;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class Tag {
    @Id @GeneratedValue
    @Column(name = "tag_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private Speech speech;

    @Column(name = "tag_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public Tag(Speech speech, String name) {
        this.speech = speech;
        this.name = name;
    }

    public Tag(Place place, KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO) {
        this.place = place;
        this.speech = korPlaceInfoUpdateDTO.getSpeech();
        this.name = korPlaceInfoUpdateDTO.getTags();
    }

    public Tag(Place place, EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO) {
        this.place = place;
        this.speech = engPlaceInfoUpdateDTO.getSpeech();
        this.name = engPlaceInfoUpdateDTO.getTags();
    }
}
