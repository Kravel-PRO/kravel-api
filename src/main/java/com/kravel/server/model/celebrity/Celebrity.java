package com.kravel.server.model.celebrity;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.CelebrityMedia;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Celebrity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "celebrity_id")
    private long id;

    @Column(name = "celebrityName")
    private String name;

    @Lob
    private String imageUrl;

    @OneToMany(mappedBy = "celebrity")
    private List<CelebrityMedia> celebrityMedias = new ArrayList<>();

    @OneToMany(mappedBy = "celebrity")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "celebrity")
    private List<PlaceCelebrity> placeCelebrities = new ArrayList<>();

    @Builder
    public Celebrity(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
