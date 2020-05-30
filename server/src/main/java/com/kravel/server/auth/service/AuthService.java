package com.kravel.server.auth.service;

import com.kravel.server.auth.mapper.AccountMapper;
import com.kravel.server.auth.model.Account;
import com.kravel.server.common.util.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encodePassword(Account account) {
        return passwordEncoder.encode(account.getLoginPw());
    }

    public int signUpAccount(Account account) throws Exception {
        if (accountMapper.checkDuplicateLoginEmail(account.getLoginEmail()) > 0) {
            return 0;
        }
        account.setLoginPw(encodePassword(account));
        return accountMapper.saveAccount(account);
    }

}
