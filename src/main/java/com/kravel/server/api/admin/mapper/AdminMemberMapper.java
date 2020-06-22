package com.kravel.server.api.admin.mapper;

import com.kravel.server.api.admin.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AdminMemberMapper {

    public List<MemberDTO> findAllMembers(@Param("param") Map<String, Object> param);
}
