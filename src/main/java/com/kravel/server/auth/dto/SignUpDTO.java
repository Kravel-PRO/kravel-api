package com.kravel.server.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpDTO {
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;
}
