package com.kravel.server.model.member;

import com.kravel.server.model.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class InquireImage extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "inquire_image_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquire_id")
    private Inquire inquire;

    @Lob
    private String imageUrl;
}
