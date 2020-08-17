package com.kravel.server.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
//@Builder
@ToString
public class SignUpDTO {
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;
}
