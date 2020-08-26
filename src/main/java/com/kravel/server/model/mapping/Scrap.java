package com.kravel.server.model.mapping;

import com.kravel.bella.model.BaseTimeEntity;
import com.kravel.bella.model.member.Member;
import com.kravel.bella.model.place.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Scrap extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "scrap_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

}
