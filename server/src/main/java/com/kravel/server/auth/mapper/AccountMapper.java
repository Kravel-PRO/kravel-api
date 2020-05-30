package com.kravel.server.auth.mapper;

import com.kravel.server.auth.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper {

    public Account findByLoginEmail(@Param("loginEmail") String loginEmail);

    public Account findByAccountId(@Param("accountId") int accountId);

    public int saveAccount(@Param("account") Account account);

    public int checkDuplicateLoginEmail(@Param("loginEmail") String loginEmail);

    public int updateAccount(@Param("accountId") int accountId, @Param("account") Account account);

    public int deleteAccount(@Param("accountId") int accountId);
}
