package com.kravel.server.model.media;

import com.kravel.server.dto.update.media.EngMediaInfoUpdateDTO;
import com.kravel.server.dto.update.media.KorMediaInfoUpdateDTO;
import com.kravel.server.enums.Speech;
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

    @Enumerated(EnumType.STRING)
    private Speech speech;

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

    public MediaInfo(KorMediaInfoUpdateDTO korInfo) {
        this.speech = Speech.KOR;
        this.title = korInfo.getTitle();
        this.contents = korInfo.getContents();
    }

    public MediaInfo(EngMediaInfoUpdateDTO engInfo) {
        this.speech = Speech.ENG;
        this.title = engInfo.getTitle();
        this.contents = engInfo.getContents();
    }
}
