package com.kravel.server.dto.update;

import com.kravel.server.enums.Speech;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDTO {
    private String loginPw;
    private String modifyLoginPw;
    private String gender;
    private String nickName;
    private Speech speech;
}
