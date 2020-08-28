package com.kravel.server.model.member;

import com.kravel.server.auth.model.RoleType;
import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.mapping.Scrap;
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

    private String speech;
    private String useAt = "Y";

    @OneToOne(mappedBy = "member")
    private Review review;

    @OneToMany(mappedBy = "member")
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();

    public void changeLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    @Builder
    public Member(long id, String loginEmail, String loginPw, String gender, RoleType roleType, String speech) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.loginPw = loginPw;
        this.gender = gender;
        this.roleType = roleType;
        this.speech = speech;
    }
}
