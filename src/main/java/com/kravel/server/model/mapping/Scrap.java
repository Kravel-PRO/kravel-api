package com.kravel.server.model.mapping;

import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.place.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Scrap extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "scrap_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public Scrap(Member member, Place place) {
        this.member = member;
        this.place = place;
    }
}
