package com.kravel.server.auth.security.factory;

import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.model.AccountContext;
import com.kravel.server.auth.model.UserRole;
import com.kravel.server.auth.security.util.jwt.JwtFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtFactoryTest {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private AccountContext context;

    @Autowired
    private JwtFactory factory;

    @Before
    public void setUp() {
        Account account = new Account();
        account.setLoginEmail("ooeunz@gmail.com");
        account.setLoginPw("1234");
        account.setUserRole(UserRole.USER);

        this.context = AccountContext.fromAccountModel(account);
    }

    @Test
    public void TEST_JWT_GENERATE() {
        log.error(factory.generateToken(this.context));
    }
}