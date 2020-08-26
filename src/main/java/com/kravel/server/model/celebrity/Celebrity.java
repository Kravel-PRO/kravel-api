package com.kravel.server.model.celebrity;

import com.kravel.bella.model.BaseEntity;
import com.kravel.bella.model.mapping.CelebrityMedia;
import com.kravel.bella.model.mapping.PlaceCelebrity;
import com.kravel.bella.model.review.Review;
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

    private String name;
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
