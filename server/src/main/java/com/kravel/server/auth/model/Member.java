package com.kravel.server.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("member")
public class Member {
    private Long memberId;

    private String loginEmail;
    private String loginPw;
    private String comparedCurPw;
    private String langu;

    private String nickName;

    private String gender;

    private String createDe;
    private String updateDe;

    private Role role;

    private Long socialId;

    private String socialProfilePic;
    private String useAt;
}
