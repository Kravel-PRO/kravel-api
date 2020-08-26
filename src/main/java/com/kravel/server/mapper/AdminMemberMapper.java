package com.kravel.server.mapper;

import com.kravel.server.dto.MemberDTO;
import com.kravel.server.model.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AdminMemberMapper {

    public List<MemberDTO> findAllMembers(@Param("param") Map<String, Object> param);

    public int findAllMembersCount(@Param("param") Map<String, Object> param);

    public Member findMemberById(@Param("param") Map<String, Object> param);
}
