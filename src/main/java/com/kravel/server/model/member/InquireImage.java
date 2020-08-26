package com.kravel.server.model.member;

import com.kravel.bella.model.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class InquireImage extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "inquire_image_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "inquire_id")
    private Inquire inquire;

    private String imageUrl;
}
