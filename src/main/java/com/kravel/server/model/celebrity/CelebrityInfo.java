package com.kravel.server.model.celebrity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CelebrityInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String speech;

    @Column(name = "celebrity_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;

    @Builder
    public CelebrityInfo(String speech) {
    }
}
