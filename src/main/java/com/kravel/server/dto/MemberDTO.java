package com.kravel.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {
    private int idx;
    private long memberId;
    private String loginEmail;
    private String loginPw;
    private String nickName;
    private String gender;
    private String roleType;
    private Speech speech;
    private String createdDate;
    private String modifiedDate;
    private String useAt;

    @JsonIgnore
    private String token;

    public static MemberDTO fromEntity(Member entity) {
        MemberDTO memberDTO =new MemberDTO();
        memberDTO.setMemberId(entity.getId());
        memberDTO.setLoginEmail(entity.getLoginEmail());
        memberDTO.setNickName(entity.getNickName());
        memberDTO.setGender(entity.getGender());
        memberDTO.setRoleType(entity.getRoleType().getRoleName());
        memberDTO.setSpeech(entity.getSpeech());
        memberDTO.setCreatedDate(entity.getCreatedDate().toString());
        memberDTO.setModifiedDate(entity.getModifiedDate().toString());

        return memberDTO;
    }
}