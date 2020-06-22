package com.kravel.server.api.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("memberDTO")
public class MemberDTO {
    private long memberId;
    private String gender;
    private String role;
    private String langu;
    private String createDe;
    private String updateDe;
    private String useAt;
}