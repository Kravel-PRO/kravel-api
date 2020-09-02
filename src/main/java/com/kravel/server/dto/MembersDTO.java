package com.kravel.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MembersDTO {
    private int totalCount;
    private List<MemberDTO> members;
}
