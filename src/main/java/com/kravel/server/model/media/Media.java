package com.kravel.server.model.media;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.CelebrityMedia;
import com.kravel.server.model.place.Place;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private long id;

    @Lob
    private String imageUrl;

    @Column(name = "open_year")
    private LocalDate year;

    private String useAt = "Y";

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    private List<MediaInfo> mediaInfos = new ArrayList<>();

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    private List<CelebrityMedia> celebrityMedias = new ArrayList<>();

    @OneToMany(mappedBy = "media")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "media")
    private List<Place> places = new ArrayList<>();
}
