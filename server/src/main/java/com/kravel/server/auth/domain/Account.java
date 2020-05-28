package com.kravel.server.auth.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Long accountId;
    private String userName;
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String phoneNum;
    private String gender;
    private String createDe;
    private String updateDe;
    private UserRole userRole;

    private Long socialId;
    private String socialProfilePic;

    private String useAt;
}
