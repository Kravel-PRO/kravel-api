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

    public boolean signUpAccount(Account account) throws Exception {

        if (accountMapper.checkDuplicateLoginEmail(account.getLoginEmail()) > 0) {
            throw new InvalidRequestException("Login Email is already exist");
        }

        account.setLoginPw(encodePassword(account));
        return accountMapper.saveAccount(account) != 0;
    }

    public boolean updateAccountLoginPw(int accountId, Account account) throws Exception {

        Account savedAccount = accountMapper.findByAccountId(accountId);
        if (!passwordEncoder.matches(account.getCheckPw(), savedAccount.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        account.setLoginPw(encodePassword(account));
        return accountMapper.updateAccountLoginPw(accountId, account) != 0;
    }

    public boolean updateAccountNickName(int accountId, Account account) throws Exception {

        account.setLoginPw(encodePassword(account));
        return accountMapper.updateAccountNickName(accountId, account) != 0;
    }

    public boolean deleteAccount(int accountId, Account account) throws Exception {

        Account savedAccount = accountMapper.findByAccountId(accountId);
        if (!passwordEncoder.matches(account.getCheckPw(), savedAccount.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        return accountMapper.deleteAccount(accountId) != 0;
    }

}
