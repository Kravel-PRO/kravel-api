package com.kravel.server.auth.mapper;

import com.kravel.server.auth.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper {

    public Account selectAccount(String loginEmail);
}
