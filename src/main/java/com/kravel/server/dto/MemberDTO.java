package com.kravel.server.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter @Setter
@Alias("memberDTO")
public class MemberDTO {
    private int idx;
    private long memberId;
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;
    private String roleType;
    private String speech;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String useAt;
}