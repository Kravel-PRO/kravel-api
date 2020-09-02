package com.kravel.server.model.media;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.CelebrityMedia;
import com.kravel.server.model.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Media extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "media_id")
    private long id;

    @Column(name = "mediaName")
    private String name;

    @Lob
    private String imageUrl;

    private String contents;

    @Column(name = "open_year")
    private LocalDate year;
    private String useAt;

    @OneToMany(mappedBy = "media")
    private List<CelebrityMedia> celebrityMedias = new ArrayList<>();

    @OneToMany(mappedBy = "media")
    private List<Review> reviews = new ArrayList<>();

    public void changeMediaName(String mediaName) {
        this.name = mediaName;
    }

}
