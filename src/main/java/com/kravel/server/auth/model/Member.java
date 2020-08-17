package com.kravel.server.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("member")
@ToString
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
