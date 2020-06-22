package com.kravel.server.api.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@Alias("membersDTO")
public class MembersDTO {
    private int totalCount;
    private List<MemberDTO> members;
}
