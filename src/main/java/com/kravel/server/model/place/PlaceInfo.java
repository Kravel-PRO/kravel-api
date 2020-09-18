package com.kravel.server.model.place;

import com.kravel.server.dto.update.place.EngPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.place.KorPlaceInfoUpdateDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PlaceInfo extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_info_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Enumerated(EnumType.STRING)
    private Speech speech;

    private String title;
    private String location;

    public PlaceInfo(Place place, KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO) {
        this.place = place;
        this.speech = korPlaceInfoUpdateDTO.getSpeech();
        this.title = korPlaceInfoUpdateDTO.getTitle();
        this.location = korPlaceInfoUpdateDTO.getLocation();
    }

    public PlaceInfo(Place place, EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO) {
        this.place = place;
        this.speech = engPlaceInfoUpdateDTO.getSpeech();
        this.title = engPlaceInfoUpdateDTO.getTitle();
        this.location = engPlaceInfoUpdateDTO.getLocation();
    }

}
