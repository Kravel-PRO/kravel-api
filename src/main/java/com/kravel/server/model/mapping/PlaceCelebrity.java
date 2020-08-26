package com.kravel.server.model.mapping;

import com.kravel.bella.model.BaseEntity;
import com.kravel.bella.model.celebrity.Celebrity;
import com.kravel.bella.model.place.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PlaceCelebrity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "place_celebrity_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;
}
