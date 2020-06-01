package com.kravel.server.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("member")
public class Member {
    private Long memberId;

    private String loginEmail;
    private String loginPw;
    private String comparedCurPw;
    private String language;

    private String nickName;

    private String gender;

    private String createDe;
    private String updateDe;

    private Role role;

    private Long socialId;

    private String socialProfilePic;
    private String useAt;
}
