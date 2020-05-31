package com.kravel.server.auth.mapper;

import com.kravel.server.auth.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthMapper {

    public Member findByLoginEmail(@Param("loginEmail") String loginEmail);

    public Member findByMemberId(@Param("loginEmail") String loginEmail);

    public int saveMember(@Param("member") Member member);

    public int checkDuplicateLoginEmail(@Param("loginEmail") String loginEmail);


}
