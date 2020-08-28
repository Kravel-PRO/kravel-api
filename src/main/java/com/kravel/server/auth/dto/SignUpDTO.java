package com.kravel.server.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpDTO {
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;
    private String speech;
}
