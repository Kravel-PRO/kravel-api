package com.kravel.server.auth.mapper;

import com.kravel.server.auth.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {

    public Member findByLoginEmail(@Param("loginEmail") String loginEmail);

    public Member findByMemberId(@Param("loginEmail") String loginEmail);

    public int saveMember(@Param("member") Member member);

    public int checkDuplicateLoginEmail(@Param("loginEmail") String loginEmail);

    public int updateMemberLoginPw(@Param("loginEmail") String loginEmail, @Param("member") Member member);

    public int updateMemberNickName(@Param("loginEmail") String loginEmail, @Param("member") Member member);

    public int deleteMember(@Param("loginEmail") String loginEmail);
}
