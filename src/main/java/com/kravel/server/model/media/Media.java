package com.kravel.server.model.media;

import com.kravel.bella.model.BaseEntity;
import com.kravel.bella.model.mapping.CelebrityMedia;
import com.kravel.bella.model.review.Review;
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
    private String mediaName;

    private String imageUrl;

    @Lob
    private String explain;

    private LocalDate year;
    private String useAt;

    @OneToMany(mappedBy = "media")
    private List<CelebrityMedia> celebrityMedias = new ArrayList<>();

    @OneToMany(mappedBy = "media")
    private List<Review> reviews = new ArrayList<>();

    public void changeMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

}
