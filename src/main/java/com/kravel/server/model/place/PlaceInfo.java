package com.kravel.server.model.place;

import com.kravel.server.dto.update.PlaceInfoUpdateDTO;
import com.kravel.server.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PlaceInfo extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "place_info_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private String speech;
    private String title;

    @Lob
    private String contents;

    public PlaceInfo(PlaceInfoUpdateDTO placeInfoUpdateDTO) {
        this.speech = placeInfoUpdateDTO.getSpeech();
        this.title = placeInfoUpdateDTO.getTitle();
        this.contents = placeInfoUpdateDTO.getContents();
    }

}
