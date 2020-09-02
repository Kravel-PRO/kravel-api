package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDTO {
    private String loginPw;
    private String compareLoginPw;
    private String gender;
    private String nickName;
}
