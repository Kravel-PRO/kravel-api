package com.kravel.server.model.member;

import com.kravel.bella.model.BaseTimeEntity;
import com.kravel.bella.model.member.enums.InquireCategory;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Inquire extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "inquire_id")
    private long id;

    private String subject;
    private String contents;

    @Enumerated(EnumType.STRING)
    private InquireCategory inquireCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "inquire")
    private List<InquireImage> inquireImages = new ArrayList<>();
}
