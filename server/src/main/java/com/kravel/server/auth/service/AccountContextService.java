package com.kravel.server.auth.service;

import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.mapper.AccountMapper;
import com.kravel.server.auth.model.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountContextService implements UserDetailsService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            Account account = accountMapper.selectAccount(username);
            if (account.getLoginEmail().isEmpty()) {
                throw new UsernameNotFoundException("Account is empty!");
            }

            return getAccountContext(account);
    }

    private AccountContext getAccountContext(Account account) {
        return AccountContext.fromAccountModel(account);
    }
}
