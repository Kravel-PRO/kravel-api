package com.kravel.server.model.member;

import com.kravel.server.enums.RoleType;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.place.Place;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Speech speech;

    @Column(length = 5)
    private String useAt = "Y";

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private RememberMe rememberMe;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquire> inquires = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    public void changeLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }
    public void changeGender(String gender) {
        this.gender = gender;
    }
    public void changeSpeech(Speech speech) {
        this.speech = speech;
    }
    public void changeRememberMe(RememberMe rememberMe) { this.rememberMe = rememberMe; }

    @Builder
    public Member(long id, String loginEmail, String nickName, String loginPw, String gender, RoleType roleType, Speech speech, RememberMe rememberMe) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.loginPw = loginPw;
        this.nickName = nickName;
        this.gender = gender;
        this.roleType = roleType;
        this.speech = speech;
        this.rememberMe = rememberMe;
    }
}
