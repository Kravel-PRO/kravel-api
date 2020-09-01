package com.kravel.server.model.place;

import com.kravel.server.dto.update.TagDTO;
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
    private String speech;

    @Column(name = "tag_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public Tag(String speech, String name) {
        this.speech = speech;
        this.name = name;
    }

    public Tag(TagDTO tagDTO) {
        this.speech = tagDTO.getSpeech();
        this.name = tagDTO.getName();
    }
}
