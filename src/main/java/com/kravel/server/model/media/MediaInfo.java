package com.kravel.server.model.media;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MediaInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_info_id")
    private long id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    public void changeTitle(String title) {
        this.title = title;
    }

    @Builder
    public MediaInfo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
