package com.kravel.server.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormLoginDTO {

    private String loginEmail;
    private String loginPw;
}
