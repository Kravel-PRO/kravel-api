package com.kravel.server.model.mapping;

import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.media.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CelebrityMedia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @Builder
    public CelebrityMedia(Celebrity celebrity, Media media) {
        this.celebrity = celebrity;
        this.media = media;
    }
}
