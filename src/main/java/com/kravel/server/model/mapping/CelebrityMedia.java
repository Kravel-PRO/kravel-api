package com.kravel.server.model.mapping;

import com.kravel.bella.model.celebrity.Celebrity;
import com.kravel.bella.model.media.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CelebrityMedia {

    @Id @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @Builder
    public CelebrityMedia(Celebrity celebrity, Media media) {
        this.celebrity = celebrity;
        this.media = media;
    }
}
