package com.kravel.server.model.member;

import com.kravel.bella.model.BaseTimeEntity;
import com.kravel.bella.model.mapping.ReviewLike;
import com.kravel.bella.model.mapping.Scrap;
import com.kravel.bella.model.member.enums.RoleType;
import com.kravel.bella.model.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private long id;
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String langu;
    private String useAt;

    @OneToOne(mappedBy = "member")
    private Review review;

    @OneToMany(mappedBy = "member")
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();
}
