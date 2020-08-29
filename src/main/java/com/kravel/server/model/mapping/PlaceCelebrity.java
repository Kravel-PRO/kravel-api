package com.kravel.server.model.mapping;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.place.Place;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;
}
