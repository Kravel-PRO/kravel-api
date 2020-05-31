package com.kravel.server.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("account")
public class Account {
    private Long accountId;
    private String userName;
    private String loginEmail;
    private String loginPw;
    private String checkPw;
    private String nickName;
    private String gender;
    private String createDe;
    private String updateDe;
    private UserRole userRole;

    private Long socialId;
    private String socialProfilePic;

    private String useAt;
}
