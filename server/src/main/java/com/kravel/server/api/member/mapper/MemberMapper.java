package com.kravel.server.api.member.mapper;

import com.kravel.server.auth.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MemberMapper {

    public int modifyMemberLoginPw(@Param("loginEmail") String loginEmail, @Param("member") Member member);

    public int modifyMemberNickName(@Param("loginEmail") String loginEmail, @Param("member") Member member);

    public int removeMember(@Param("loginEmail") String loginEmail);
}
