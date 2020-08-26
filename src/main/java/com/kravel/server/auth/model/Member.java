package com.kravel.server.auth.model;

import com.kravel.server.api.model.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("member")
@ToString
public class Member extends BaseTimeEntity {
    private Long memberId;
    private String loginEmail;
    private String loginPw;
    private String comparedCurPw;

    private String speech;
    private String nickName;
    private String gender;
    private RoleType roleType;
    private Long socialId;
    private String useAt;
}
